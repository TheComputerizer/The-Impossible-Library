package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core;

import cpw.mods.jarhandling.JarContents;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.NeoForgeCoreLoader;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.NeoForgeModLoading;
import net.neoforged.neoforgespi.ILaunchContext;
import net.neoforged.neoforgespi.locating.IDiscoveryPipeline;
import net.neoforged.neoforgespi.locating.IModFile;
import net.neoforged.neoforgespi.locating.IModFileCandidateLocator;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;

/**
 * Since this library is loaded as a service, we need to tell NeoForge that it's also a mod
 */
public class TILSelfLocator extends TILModFinderNeoForge1_21 implements IModFileCandidateLocator {
    
    private static final CoreAPI CORE;
    private static final String JAR_EXT = ".jar";
    private static final String IMPL_PKG = "mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core";
    private static final String LOCATING_PKG = "net.neoforged.neoforgespi.locating";
    private static final String MOD_FILE_IMPL = IMPL_PKG+".TILSelfLocator";
    private static final String MOD_FILE_SERVICE = LOCATING_PKG+".IModFileCandidateLocator";
    private static final String MOD_LANGUAGE_IMPL = IMPL_PKG+".MultiVersionLanguageLoader";
    private static final String MOD_LANGUAGE_SERVICE = LOCATING_PKG+".IModLanguageLoader";
    private static final String MOD_READER_IMPL = IMPL_PKG+".MultiVersionModReader";
    private static final String MOD_READER_SERVICE = LOCATING_PKG+".IModFileReader";
    private static final boolean WINDOWS = System.getProperty("os.name").toLowerCase().contains("windows");
    
    static {
        Class<?> c = TILSelfLocator.class;
        ClassLoader loader = c.getClassLoader();
        NeoForgeCoreLoader.fixForServiceLayer();
        CORE = (CoreAPI)NeoForgeCoreLoader.initCoreAPI(loader);
        if(Objects.isNull(CORE))
            throw new RuntimeException("Failed to retrieve CoreAPI instance for TILSelfLocator");
        if(!NeoForgeModLoading.setLoadingVersion(c,CORE))
            throw new RuntimeException("Failed to set mod loading version for TILSelfLocator!");
        if(loader!=NeoForgeCoreLoader.bootLoader()) {
            NeoForgeCoreLoader.fixService(MOD_FILE_SERVICE,MOD_FILE_IMPL,loader,true);
            NeoForgeCoreLoader.fixService(MOD_LANGUAGE_SERVICE,MOD_LANGUAGE_IMPL,loader);
            NeoForgeCoreLoader.fixService(MOD_READER_SERVICE,MOD_READER_IMPL,loader);
        }
    }
    
    public TILSelfLocator() {
        super(CORE);
    }
    
    boolean addSelf(URL url, IDiscoveryPipeline pipeline) throws Exception {
        this.logger.info("Attempting to read self from URL {}",url);
        Path path = Paths.get(url.toURI()).toAbsolutePath();
        this.logger.info("Attempting to read self from path {}",path);
        IModFile file = findAndLoad(JarContents.of(path),() -> this);
        if(Objects.nonNull(file)) {
            pipeline.addModFile(file);
            return true;
        }
        this.logger.error("Read IModFile instance as null!");
        return false;
    }
    
    @Override public void findCandidates(ILaunchContext context, IDiscoveryPipeline pipeline) {
        try {
            if(addSelf(selfLocation(),pipeline)) this.logger.info("Successfully added mod file for {}",MODID);
            else this.logger.error("Failed to add self mod! Any dependents trying to load will likely break!");
        } catch(Throwable t) {
            this.logger.error("Caught exception while trying to add self mod!",t);
        }
    }
    
    //TODO Needs to be verified or maybe there's a better way of extracting the path
    String fixPath(String path) {
        if(WINDOWS && (path.startsWith("/") || path.startsWith("\\"))) path = path.substring(1);
        if(path.contains(JAR_EXT) && !path.endsWith(JAR_EXT))
            path = path.substring(0,path.lastIndexOf(JAR_EXT)+JAR_EXT.length());
        return path.replace("%20"," ");
    }
    
    /**
     * Try loading self before everything except the most important locators
     */
    @Override public int getPriority() {
        return HIGHEST_SYSTEM_PRIORITY-1;
    }
}