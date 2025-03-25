package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import cpw.mods.modlauncher.Launcher;
import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.ForgeModLoading;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.forgespi.locating.IModLocator;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.security.CodeSigner;
import java.util.*;
import java.util.function.Consumer;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

@Getter
public class MultiVersionModLocator implements IModLocator {
    
    static {
        ForgeCoreLoader.fixFirstEntryPoint();
        Object instance = ForgeCoreLoader.initCoreAPI(MultiVersionModLocator.class.getClassLoader());
        if(Objects.isNull(instance))
            throw new RuntimeException("Failed to retrieve CoreAPI instance for MultiVersionModLocator");
    }
    
    private Map<IModFile,FileSystem> fileSystems;
    boolean failed;
    
    public MultiVersionModLocator() {
        TILRef.logInfo("Core Forge Locator plugin loaded on {}",getClass().getClassLoader());
        setLoadingVersion(ForgeCoreLoader.initCoreAPI(Launcher.class.getClassLoader()));
    }
    
    void setLoadingVersion(@Nullable Object coreInstance) {
        if(Objects.isNull(coreInstance)) {
            TILRef.logError("Failed to set Forge mod loading version with null CoreAPI instance!");
            this.failed = true;
            return;
        }
        ClassHelper.checkBurningWaveInit();
        String version = String.valueOf((Object)Methods.invoke(coreInstance,"gameVersion"));
        String checkedVersion = version.substring(2).replace('.','_');
        ForgeModLoading.setFileVersion(getClass(),checkedVersion,version);
        TILRef.logInfo("Successfully set Forge mod loading version ({},{})",checkedVersion,version);
    }
    
    FileSystem fileSystemFor(IModFile file) {
        try {
            return FileSystems.newFileSystem(file.getFilePath(),file.getClass().getClassLoader());
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
        try(JarFile jar = new JarFile(path.toFile())) {
            Manifest manifest = jar.getManifest();
            Optional<Manifest> optionalManifest = Objects.nonNull(manifest) ? Optional.of(manifest) : Optional.empty();
            return Pair.of(optionalManifest,Optional.empty());
        } catch(Throwable t) {
            TILRef.logError("Failed to find manifest & signers for {}",path,t);
            return Pair.of(Optional.empty(),Optional.empty());
        }
    }
    
    /**
     * Used in 1.16.5
     */
    public Path findPath(IModFile modFile, String ... path) {
        if(path.length<1) throw new IllegalArgumentException("Missing path");
        else return this.fileSystems.get(modFile).getPath("",path);
    }
    
    @Override public void scanFile(IModFile file, Consumer<Path> consumer) {}
    
    @Override public void initArguments(Map<String,?> arguments) {
        if(this.failed) {
            TILRef.logWarn("Not initializing mod loading for MultiVersionModLocator that failed to load");
            return;
        }
        TILRef.logInfo("Initializing Forge mod loading with args {}",arguments);
        ForgeModLoading.initModLoading(getClass().getClassLoader(),this);
    }
    
    @Override public boolean isValid(IModFile file) {
        return !this.failed;
    }
    
    @Override public String name() {
        return "multiversionloader";
    }
    
    @Override public List<IModFile> scanMods() {
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
                }
            }
            TILRef.logInfo("Returing scanned mods {}",files);
            return files;
        } catch(Throwable t) {
            TILRef.logError("Failed to scan mods",t);
            throw t;
        }
    }
}