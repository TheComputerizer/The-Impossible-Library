package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core;

import cpw.mods.jarhandling.JarContents;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModFinder;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.NeoForgeCoreLoader;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core.loader.TILModFileNeoForge1_21;
import net.neoforged.neoforgespi.ILaunchContext;
import net.neoforged.neoforgespi.locating.IDiscoveryPipeline;
import net.neoforged.neoforgespi.locating.IModFile;
import net.neoforged.neoforgespi.locating.IModFileCandidateLocator;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Collection;
import java.util.Objects;
import java.util.jar.Manifest;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;

/**
 * Since this library is loaded as a service, we need to tell NeoForge that it's also a mod
 */
public class TILSelfLocator implements IModFileCandidateLocator {
    
    private static final CoreAPI CORE;
    private static final String JAR_EXT = ".jar";
    private static final String IMPL_PKG = "mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core";
    private static final String LOCATING_PKG = "net.neoforged.neoforgespi.locating";
    private static final Logger LOGGER = LoggerFactory.getLogger("TILSelfLocator");
    private static final String MOD_FILE_IMPL = IMPL_PKG+".TILSelfLocator";
    private static final String MOD_FILE_SERVICE = LOCATING_PKG+".IModFileCandidateLocator";
    private static final String MOD_LANGUAGE_IMPL = IMPL_PKG+".MultiVersionLanguageLoader";
    private static final String MOD_LANGUAGE_SERVICE = LOCATING_PKG+".IModLanguageLoader";
    private static final String MOD_READER_IMPL = IMPL_PKG+".MultiVersionModReader";
    private static final String MOD_READER_SERVICE = LOCATING_PKG+".IModFileReader";
    
    static {
        ClassLoader loader = MultiVersionModReader.class.getClassLoader();
        NeoForgeCoreLoader.fixForServiceLayer();
        CORE = (CoreAPI)NeoForgeCoreLoader.initCoreAPI(loader);
        if(Objects.isNull(CORE))
            throw new RuntimeException("Failed to retrieve CoreAPI instance for TILSelfLocator");
        if(loader!=NeoForgeCoreLoader.bootLoader()) {
            NeoForgeCoreLoader.fixService(MOD_FILE_SERVICE,MOD_FILE_IMPL,loader,true);
            NeoForgeCoreLoader.fixService(MOD_LANGUAGE_SERVICE,MOD_LANGUAGE_IMPL,loader);
            NeoForgeCoreLoader.fixService(MOD_READER_SERVICE,MOD_READER_IMPL,loader);
        }
    }
    
    private final MultiVersionLoaderAPI loader;
    
    public TILSelfLocator() {
        this.loader = CORE.getLoader();
        LOGGER.info("Successfully initialized!");
    }
    
    boolean addSelf(URL url, IDiscoveryPipeline pipeline) {
        LOGGER.info("Attempting to read self from URL {}",url);
        return addSelf(Paths.get(fixPath(url.getPath())),pipeline);
    }
    
    boolean addSelf(Path path, IDiscoveryPipeline pipeline) {
        LOGGER.info("Attempting to read self from path {}",path);
        IModFile file = readSelf(JarContents.of(path));
        if(Objects.nonNull(file)) {
            pipeline.addModFile(file);
            return true;
        }
        LOGGER.error("Read IModFile instance as null!");
        return false;
    }
    
    @Override public void findCandidates(ILaunchContext context, IDiscoveryPipeline pipeline) {
        Class<?> c = getClass();
        ProtectionDomain pd = getClass().getProtectionDomain();
        if(Objects.nonNull(pd)) {
            CodeSource source = pd.getCodeSource();
            if(Objects.nonNull(source)) {
                if(addSelf(source.getLocation(),pipeline)) LOGGER.info("Successfully added mod file for {}",MODID);
                else LOGGER.error("Failed to add self mod! Any dependents trying to load will likely break!");
            } else LOGGER.error("CodeSource instance for {} did not exist!",c);
        } else LOGGER.error("ProtectionDomain instance for {} did not exist!",c);
    }
    
    String fixPath(String path) {
        if(path.startsWith("/") || path.startsWith("\\")) path = path.substring(1);
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
    
    @Nullable MultiVersionModCandidate mergeCandidates(@Nullable MultiVersionModCandidate candidate1,
            @Nullable MultiVersionModCandidate candidate2) {
        if(Objects.nonNull(candidate1)) {
            if(Objects.nonNull(candidate2)) candidate1.merge(candidate2);
            return candidate1;
        }
        return candidate2;
    }
    
    boolean queryFile(String loaderName, File file) {
        String fileName = file.getName();
        TILDev.logInfo("[{}]: Checking if file {} is the loader", loaderName, fileName);
        if(Objects.isNull(MultiVersionModCandidate.loaderFile) && TILDev.isLoader(fileName)) {
            TILDev.logInfo("[{}]: File is the loader",loaderName);
            MultiVersionModCandidate.loaderFile = file;
        }
        return false;
    }
    
    @Nullable IModFile readSelf(JarContents jar) {
        MultiVersionModCandidate candidate = null;
        Manifest manifest = jar.getManifest();
        if(Objects.nonNull(manifest) && MultiVersionModFinder.hasMods(manifest.getMainAttributes())) {
            Path path = jar.getPrimaryPath();
            this.loader.addPotentialModPath(path);
            String loaderName = this.loader.getName();
            TILRef.logInfo("[{}]: Found mod candidate at {}",loaderName,path);
            File file = path.toFile();
            if(queryFile(loaderName,file)) return null;
            candidate = mergeCandidates(MultiVersionModFinder.discoverCoreCandidate(this.loader,file),
                                        MultiVersionModFinder.discoverModCandidate(this.loader,file));
        }
        if(Objects.isNull(candidate)) return null;
        Collection<?> infos = CORE.loadCandidate(candidate,this.loader,getClass().getClassLoader());
        return infos.isEmpty() ? null : new TILModFileNeoForge1_21(this,candidate,infos);
    }
}