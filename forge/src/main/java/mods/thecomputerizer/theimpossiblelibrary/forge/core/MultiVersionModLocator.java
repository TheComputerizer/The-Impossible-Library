package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.ForgeModLoading;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.forgespi.locating.IModLocator;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.security.CodeSigner;
import java.util.*;
import java.util.function.Consumer;
import java.util.jar.Manifest;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;

@Getter
public class MultiVersionModLocator implements IModLocator {
    
    static MultiVersionModLocator instance;
    
    static ClassLoader modFileClassLoader(IModFile file) {
        if(Objects.isNull(file)) return IModFile.class.getClassLoader();
        Class<?> cls = file.getClass();
        TILRef.logInfo("Finding ClassLoader for file {}({})",file.getFileName(),cls);
        return ("TILForgeModFile".equals(cls.getSimpleName()) ? ForgeModLoading.class : cls).getClassLoader();
    }
    
    private Map<IModFile,FileSystem> fileSystems;
    boolean failed;
    /**
     * Due to version loading differences it is possible for multiple MultiVersionModLocator instances to be loaded.
     * We want to make sure only one of these instances will attempt to load mods.
     */
    boolean initialized;
    
    public MultiVersionModLocator() {
        ClassLoader thisLoader = getClass().getClassLoader();
        if(Objects.isNull(instance)) {
            TILRef.logInfo("Core Forge Locator plugin loaded on {}",thisLoader);
            instance = this;
        } else TILRef.logDebug("Extra core Forge Locator plugin loaded on {}",thisLoader);
    }
    
    FileSystem fileSystemFor(IModFile file) {
        try {
            Path path = file.getFilePath();
            if(TILDev.isLoaderPath(path)) {
                TILRef.logInfo("Subverting the loader file system since this is a dev environment (-Dtil.dev=true)");
                TILDev.checkDevPath(path);
                return null;
            }
            return FileSystems.newFileSystem(path,modFileClassLoader(file));
        } catch(IOException ex) {
            TILRef.logError("Could not create file system for {}",file.getFilePath(),ex);
        }
        return null;
    }
    
    /**
     * Used in 1.16.5
     */
    public Optional<Manifest> findManifest(Path path) {
        return this.findManifestAndSigners(path).getKey();
    }
    
    /**
     * Used in 1.16.5
     */
    public Pair<Optional<Manifest>,Optional<CodeSigner[]>> findManifestAndSigners(Path path) {
        return Pair.of(ForgeModLoading.findManifest(path),Optional.empty());
    }
    
    /**
     * Used in 1.16.5
     */
    public Path findPath(IModFile modFile, String ... paths) {
        if(paths.length<1) throw new IllegalArgumentException("Missing path");
        else {
            ForgeModLoading.queryCoreMods(paths);
            if(DEV && TILDev.isLoaderPath(modFile.getFilePath())) return TILDev.getLoaderResourcePath(paths);
            if(this.fileSystems.containsKey(modFile)) return this.fileSystems.get(modFile).getPath("",paths);
            TILRef.logError("Unable to find paths {} for {}",paths,modFile.getFileName());
            return null;
        }
    }
    
    @Override public void initArguments(Map<String,?> arguments) {
        if(this.failed) {
            TILRef.logWarn("Not initializing mod loading for MultiVersionModLocator that failed to load");
            return;
        }
        this.initialized = ForgeModLoading.initModLoading(getClass().getClassLoader(),this,arguments);
    }
    
    @Override public boolean isValid(IModFile file) {
        return !this.failed;
    }
    
    @Override public String name() {
        return "multiversionloader";
    }
    
    @Override public void scanFile(IModFile file, Consumer<Path> consumer) {}
    
    @Override public List<IModFile> scanMods() {
        if(!this.initialized) {
            TILRef.logDebug("Skipping mod scan for uninitialized MultiVersionModLocator");
            return Collections.emptyList();
        }
        if(this.failed) {
            TILRef.logWarn("Not scanning for mods with MultiVersionModLocator that failed to load");
            return Collections.emptyList();
        }
        TILRef.logInfo("Scanning for mods");
        this.fileSystems = Collections.emptyMap();
        try {
            List<IModFile> files = ForgeModLoading.scanMods();
            if(!files.isEmpty() && ForgeModLoading.isPathBased()) {
                this.fileSystems = new HashMap<>();
                for(IModFile file : files) {
                    FileSystem fs = fileSystemFor(file);
                    if(Objects.nonNull(fs)) this.fileSystems.put(file,fs);
                    else if(!DEV) TILRef.logWarn("Failed to get FileSystem for {}",file.getFileName());
                }
            }
            TILRef.logInfo("Returing {} scanned mods {}",files.size(),files);
            return files;
        } catch(Throwable t) {
            TILRef.logError("Failed to scan mods",t);
            throw t;
        }
    }
}