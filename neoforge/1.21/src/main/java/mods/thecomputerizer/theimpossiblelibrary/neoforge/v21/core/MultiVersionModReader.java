package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core;

import cpw.mods.jarhandling.JarContents;
import cpw.mods.jarhandling.JarContentsBuilder;
import cpw.mods.jarhandling.JarMetadata;
import cpw.mods.jarhandling.SecureJar;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModCandidate;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModFinder;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.NeoForgeCoreLoader;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core.loader.TILModFileNeoForge1_21;
import net.neoforged.neoforgespi.locating.IModFile;
import net.neoforged.neoforgespi.locating.IModFileReader;
import net.neoforged.neoforgespi.locating.ModFileDiscoveryAttributes;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;
import java.util.jar.Manifest;

public class MultiVersionModReader implements IModFileReader {
    
    static {
        NeoForgeCoreLoader.fixForServiceLayer();
        Object instance = NeoForgeCoreLoader.initCoreAPI(MultiVersionModReader.class.getClassLoader());
        if(Objects.isNull(instance))
            throw new RuntimeException("Failed to retrieve CoreAPI instance for MultiVersionModLocator");
    }
    
    public static SecureJar jarFromPath(Path path) {
        try(JarContents contents = new JarContentsBuilder().paths(path).build()) {
            return SecureJar.from(contents, JarMetadata.from(contents));
        } catch(IOException ex) {
            TILRef.logError("Failed to get SecureJar from {}",path,ex);
        }
        return null;
    }
    
    private final CoreAPI core;
    private final MultiVersionLoaderAPI loader;
    private MultiVersionModCandidate candidate;
    
    public MultiVersionModReader() {
        this.core = CoreAPI.getInstance();
        if(Objects.isNull(this.core))
            throw new RuntimeException("Failed to get CoreAPI instance! Did something break in NeoForgeCoreLoader?");
        this.loader = this.core.getLoader();
    }
    
    @Nullable MultiVersionModCandidate mergeCandidates(@Nullable MultiVersionModCandidate candidate1,
            @Nullable MultiVersionModCandidate candidate2) {
        if(Objects.nonNull(candidate1)) {
            if(Objects.nonNull(candidate2)) candidate1.merge(candidate2);
            return candidate1;
        }
        return candidate2;
    }
    
    void queryLoaderFile(String loaderName, File file) {
        String fileName = file.getName();
        TILDev.logInfo("[{}]: Checking if file {} is the loader",loaderName,fileName);
        if(Objects.isNull(MultiVersionModCandidate.loaderFile) && TILDev.isLoader(fileName)) {
            TILDev.logInfo("[{}]: File is the loader",loaderName);
            MultiVersionModCandidate.loaderFile = file;
        }
    }
    
    @Override public @Nullable IModFile read(JarContents jar, ModFileDiscoveryAttributes attributes) {
        Manifest manifest = jar.getManifest();
        if(Objects.nonNull(manifest) && MultiVersionModFinder.hasMods(manifest.getMainAttributes())) {
            Path path = jar.getPrimaryPath();
            this.loader.addPotentialModPath(path);
            String loaderName = this.loader.getName();
            TILRef.logInfo("[{}]: Found mod candidate at {}",loaderName,path);
            File file = path.toFile();
            queryLoaderFile(loaderName,file);
            this.candidate = mergeCandidates(MultiVersionModFinder.discoverCoreCandidate(this.loader,file),
                    MultiVersionModFinder.discoverModCandidate(this.loader,file));
        }
        if(Objects.isNull(this.candidate)) return null;
        Collection<?> infos = this.core.loadCandidate(this.candidate,this.loader,getClass().getClassLoader());
        return infos.isEmpty() ? null : new TILModFileNeoForge1_21(this,this.candidate,infos);
    }
}
