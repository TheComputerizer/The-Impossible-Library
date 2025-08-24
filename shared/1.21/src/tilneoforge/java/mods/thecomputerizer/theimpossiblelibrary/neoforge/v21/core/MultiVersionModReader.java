package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core;

import cpw.mods.jarhandling.JarContents;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModFinder;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.NeoForgeCoreLoader;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core.loader.TILModFileNeoForge1_21;
import net.neoforged.neoforgespi.locating.IModFile;
import net.neoforged.neoforgespi.locating.IModFileReader;
import net.neoforged.neoforgespi.locating.ModFileDiscoveryAttributes;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.jar.Manifest;

public class MultiVersionModReader implements IModFileReader {
    
    private static final CoreAPI CORE;
    private static final Logger LOGGER = LoggerFactory.getLogger("MultiVersionModReader");
    
    static {
        ClassLoader loader = MultiVersionModReader.class.getClassLoader();
        CORE = (CoreAPI)NeoForgeCoreLoader.initCoreAPI(loader);
        if(Objects.isNull(CORE))
            throw new RuntimeException("Failed to retrieve CoreAPI instance for TILSelfLocator");
    }
    
    static Set<String> alreadyHandled = new HashSet<>();
    
    private final MultiVersionLoaderAPI loader;
    
    public MultiVersionModReader() {
        this.loader = CORE.getLoader();
    }
    
    /**
     * Try reading multiversion mods first so they can be filtered out from normal mod loading earlier.
     * Don't use HIGHEST_SYSTEM_PRIORITY to avoid potential conflicts with important readers.
     * DEFAULT_PRIORITY = 0
     */
    @Override public int getPriority() {
        return 1;
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
        if(alreadyHandled.contains(fileName)) {
            LOGGER.info("Skipping file that was already handled {}",fileName);
            return true;
        }
        alreadyHandled.add(fileName);
        LOGGER.info("[{}]: Checking if file {} is the loader",loaderName,fileName);
        if(Objects.isNull(MultiVersionModCandidate.getLoaderFile()) && TILDev.isLoaderName(fileName)) {
            TILDev.logInfo("[{}]: File is the loader",loaderName);
            MultiVersionModCandidate.setLoaderFile(file);
        }
        return false;
    }
    
    @Override public @Nullable IModFile read(JarContents jar, ModFileDiscoveryAttributes attributes) {
        MultiVersionModCandidate candidate = null;
        Manifest manifest = jar.getManifest();
        if(Objects.nonNull(manifest) && MultiVersionModFinder.hasMods(manifest.getMainAttributes())) {
            Path path = jar.getPrimaryPath();
            this.loader.addPotentialModPath(path);
            String loaderName = this.loader.getName();
            LOGGER.info("[{}]: Found mod candidate at {}",loaderName,path);
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
