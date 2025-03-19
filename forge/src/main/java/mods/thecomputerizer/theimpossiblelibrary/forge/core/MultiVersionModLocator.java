package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import cpw.mods.modlauncher.Launcher;
import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
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
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class MultiVersionModLocator implements IModLocator {
    
    static {
        ForgeCoreLoader.fixIfNotJava8();
        Object instance = ForgeCoreLoader.initCoreAPI(MultiVersionModLocator.class.getClassLoader());
        if(Objects.isNull(instance))
            throw new RuntimeException("Failed to retrieve CoreAPI instance for MultiVersionModLocator");
    }
    
    private final Object localLocator;
    @Getter private Map<IModFile,FileSystem> fileSystems;
    
    public MultiVersionModLocator() {
        ClassLoader loader = getClass().getClassLoader();
        ClassLoader bootLoader = Launcher.class.getClassLoader();
        TILRef.logInfo("Core Forge Locator plugin loaded on {}",loader);
        if(!loader.equals(bootLoader))
            TILRef.logInfo("That's the wrong ClassLoader... Retrieving locator instance from the right "+
                           "ClassLoader {}",bootLoader);
        Object instance = ForgeCoreLoader.initCoreAPI(bootLoader);
        this.localLocator = Objects.nonNull(instance) ? ReflectionHelper.invokeMethod(instance.getClass(),
                "getModLocator",instance,new Class<?>[]{ClassLoader.class},instance.getClass().getClassLoader()) :
                null;
        if(Objects.nonNull(this.localLocator)) TILRef.logInfo("Found mod locator {}",this.localLocator.getClass());
        else TILRef.logFatal("Failed to find mod locator! Unable to load multiversion mods");
    }
    
    FileSystem fileSystemFor(IModFile file) {
        try {
            return FileSystems.newFileSystem(file.getFilePath(), file.getClass().getClassLoader());
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
    
    @Override public void initArguments(Map<String,?> arguments) {
        TILRef.logInfo("Inkoved initArguments with arguments {}",arguments);
        if(Objects.nonNull(this.localLocator)) {
            ClassLoader loader = getClass().getClassLoader();
            TILDev.logInfo("Initializing mod locator with {}",loader);
            ReflectionHelper.invokeMethod(this.localLocator.getClass(),"initFor",this.localLocator,
                                          new Class<?>[]{ClassLoader.class,IModLocator.class},loader,this);
        } else TILRef.logFatal("Locator is null and cannot load multiversion mods! Did it fail to initialize?");
        TILRef.logInfo("Finished initArguments with localLocator {}",this.localLocator);
    }
    
    @Override public boolean isValid(IModFile file) {
        TILRef.logInfo("Inkoved isValid on IModLocator for file {}",file);
        return true;
    }
    
    @Override public String name() {
        return "multiversionloader";
    }
    
    @Override public void scanFile(IModFile file, Consumer<Path> pathConsumer) {
        TILRef.logInfo("Inkoved scanFile on IModLocator for file {}",file);
    }
    
    @SuppressWarnings("unchecked")
    @Override public List<IModFile> scanMods() {
        TILRef.logInfo("Scanning for mods!!!!!!!!!!!!!!!");
        List<IModFile> files = null;
        try {
            this.fileSystems = Collections.emptyMap();
            if(Objects.nonNull(this.localLocator)) {
                files = (List<IModFile>)ReflectionHelper.invokeMethod(this.localLocator.getClass(),
                                                                      "scanMods", this.localLocator,
                                                                      new Class<?>[]{IModLocator.class}, this);
                if(Objects.nonNull(files) && this.localLocator.getClass().getSimpleName().contains("1_16_5")) {
                    this.fileSystems = new HashMap<>();
                    for(IModFile file : files) {
                        FileSystem fs = fileSystemFor(file);
                        if(Objects.nonNull(fs)) this.fileSystems.put(file, fs);
                    }
                }
            } else TILRef.logFatal("Locator is null and cannot scan for multiversion mods! Did it fail to initialize?");
        } catch(Throwable t) {
            TILRef.logError("Failed to scan mods",t);
            throw t;
        }
        TILRef.logInfo("Returing scanned mods {}",files);
        return Objects.nonNull(files) ? files : Collections.emptyList();
    }
}
