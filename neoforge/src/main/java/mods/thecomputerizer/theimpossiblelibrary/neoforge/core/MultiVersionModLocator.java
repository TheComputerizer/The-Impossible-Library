package mods.thecomputerizer.theimpossiblelibrary.neoforge.core;

import cpw.mods.modlauncher.Launcher;
import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.neoforged.neoforgespi.locating.IModFile;
import net.neoforged.neoforgespi.locating.IModLocator;

import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class MultiVersionModLocator implements IModLocator {
    
    static final String MANIFEST = "META-INF/MANIFEST.MF";
    
    static {
        NeoForgeCoreLoader.fixIfNotJava8();
        Object instance = NeoForgeCoreLoader.initCoreAPI(MultiVersionModLocator.class.getClassLoader());
        if(Objects.isNull(instance))
            throw new RuntimeException("Failed to retrieve CoreAPI instance for MultiVersionModLocator");
    }
    
    private final Object localLocator;
    @Getter private Map<IModFile,FileSystem> fileSystems;
    
    public MultiVersionModLocator() {
        ClassLoader loader = getClass().getClassLoader();
        ClassLoader bootLoader = Launcher.class.getClassLoader();
        TILRef.logInfo("Core NeoForge Locator plugin loaded on {}",loader);
        if(!loader.equals(bootLoader))
            TILRef.logInfo("That's the wrong ClassLoader... Retrieving locator instance from the right "+
                           "ClassLoader {}",bootLoader);
        Object instance = NeoForgeCoreLoader.initCoreAPI(bootLoader);
        this.localLocator = Objects.nonNull(instance) ? ReflectionHelper.invokeMethod(instance.getClass(),
                "getModLocator",instance,new Class<?>[]{ClassLoader.class},instance.getClass().getClassLoader()) :
                null;
        if(Objects.nonNull(this.localLocator)) TILRef.logInfo("Found mod locator {}",this.localLocator.getClass());
        else TILRef.logFatal("Failed to find mod locator! Unable to load multiversion mods");
    }
    
    @Override public void initArguments(Map<String,?> arguments) {
        if(Objects.nonNull(this.localLocator)) {
            ClassLoader loader = getClass().getClassLoader();
            TILDev.logInfo("Initializing mod locator with {}",loader);
            ReflectionHelper.invokeMethod(this.localLocator.getClass(),"initFor",this.localLocator,
                    new Class<?>[]{ClassLoader.class,IModLocator.class},loader,this);
        } else TILRef.logFatal("Locator is null and cannot load multiversion mods! Did it fail to initialize?");
    }
    
    @Override public boolean isValid(IModFile file) {
        return true;
    }
    
    @Override public String name() {
        return "multiversionloader";
    }
    
    @Override public void scanFile(IModFile file, Consumer<Path> pathConsumer) {}
    
    @SuppressWarnings("unchecked")
    @Override public List<ModFileOrException> scanMods() {
        List<ModFileOrException> files = null;
        if(Objects.nonNull(this.localLocator)) {
            files = (List<ModFileOrException>)ReflectionHelper.invokeMethod(this.localLocator.getClass(),
                    "scanMods",this.localLocator,new Class<?>[]{IModLocator.class},this);
        } else TILRef.logFatal("Locator is null and cannot scan for multiversion mods! Did it fail to initialize?");
        return Objects.nonNull(files) ? files : Collections.emptyList();
    }
}
