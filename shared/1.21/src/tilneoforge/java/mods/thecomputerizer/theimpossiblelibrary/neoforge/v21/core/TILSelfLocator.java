package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core;

import cpw.mods.jarhandling.JarContents;
import cpw.mods.niofs.union.UnionPath;
import net.neoforged.neoforgespi.ILaunchContext;
import net.neoforged.neoforgespi.locating.IDiscoveryPipeline;
import net.neoforged.neoforgespi.locating.IModFile;
import net.neoforged.neoforgespi.locating.IModFileCandidateLocator;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import static java.io.File.separatorChar;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModFinder.MULTIVERSION_COREMODS;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModFinder.MULTIVERSION_MODS;

/**
 * Since this library is loaded as a service, we need to tell NeoForge that it's also a mod
 */
public class TILSelfLocator extends TILModFinderNeoForge1_21 implements IModFileCandidateLocator {
    
    static final String API_PKG = "mods.thecomputerizer.theimpossiblelibrary.api";
    static final String SOURCE_NAME = "tilneoforge";
    
    static final String[] RELATIVE_SOURCE_PATHS = new String[]{
            "classes"+separatorChar+"java"+separatorChar+"main",
            "classes"+separatorChar+"java"+separatorChar+SOURCE_NAME,
            "resources"+separatorChar+SOURCE_NAME,
    };
    
    public TILSelfLocator() {
        super();
    }
    
    boolean addSelf(URL url, IDiscoveryPipeline pipeline) throws Exception {
        this.logger.info("Attempting to read self from URL {}",url);
        Path[] paths = fixedPath(url);
        this.logger.info("Attempting to read self from paths {}",Arrays.toString(paths));
        IModFile[] files = findAndLoadSelf(paths,() -> this);
        if(Objects.nonNull(files)) {
            if(files.length!=2) {
                this.logger.error("Read the wrong number of loader files?? ({} files)",files.length);
                return false;
            }
            for(IModFile file : files) pipeline.addModFile(file);
            return true;
        }
        this.logger.error("Read loader IModFile instances as null!");
        return false;
    }
    
    Path[] expandedLoaderPaths(String basePath) {
        Path[] newPaths = new Path[3];
        for(int i=0;i<RELATIVE_SOURCE_PATHS.length;i++)
            newPaths[i] = Path.of(basePath+RELATIVE_SOURCE_PATHS[i]);
        return newPaths;
    }
    
    /**
     * Returns the path with the file separator at the end
     */
    String extractBasePath(String pathStr) {
        for(String relativePath : RELATIVE_SOURCE_PATHS)
            if(pathStr.endsWith(relativePath))
                return pathStr.substring(0,pathStr.length()-relativePath.length());
        this.logger.error("Failed to extract base path from {}",pathStr);
        return pathStr;
    }
    
    @Override public void findCandidates(ILaunchContext context, IDiscoveryPipeline pipeline) {
        try {
            if(addSelf(selfLocation(),pipeline)) this.logger.info("Successfully added mod file for {}",MODID);
            else this.logger.error("Failed to add self mod! Any dependents trying to load will likely break!");
        } catch(Throwable t) {
            this.logger.error("Caught exception while trying to add self mod!",t);
        }
    }
    
    Path[] fixedPath(URL url) throws Exception {
        Path path = Paths.get(url.toURI());
        path = (path instanceof UnionPath union ? union.getFileSystem().getPrimaryPath() : path).toAbsolutePath();
        if(DEV) {
            File f = path.toFile();
            String fileName = f.getName();
            if(f.isDirectory() && ("main".equals(fileName) || SOURCE_NAME.equals(fileName)))
                return expandedLoaderPaths(extractBasePath(path.toString()));
        }
        return new Path[]{path};
    }
    
    @Override protected Manifest getManifest(JarContents jar) {
        Manifest manifest = super.getManifest(jar);
        Attributes attributes = manifest.getMainAttributes();
        attributes.putIfAbsent(MULTIVERSION_COREMODS,API_PKG+".core.TILCoreEntryPoint");
        attributes.putIfAbsent(MULTIVERSION_MODS,API_PKG+".common.TILCommonEntryPoint");
        return manifest;
    }
    
    /**
     * Try loading self before everything except the most important locators
     */
    @Override public int getPriority() {
        return HIGHEST_SYSTEM_PRIORITY-1;
    }
}