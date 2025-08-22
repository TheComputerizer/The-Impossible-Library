package mods.thecomputerizer.theimpossiblelibrary.neoforge.core;

import cpw.mods.modlauncher.Launcher;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.neoforged.neoforgespi.locating.IModFile;
import net.neoforged.neoforgespi.locating.IModLocator;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class MultiVersionModLocator implements IModLocator {
    
    private static final String IMPL_PKG = "mods.thecomputerizer.theimpossiblelibrary.neoforge.core";
    private static final String NEOFORGE_PKG = "net.neoforged.neoforgespi";
    private static final String MOD_LOCATOR_IMPL = IMPL_PKG+".MultiVersionModLocator";
    private static final String MOD_LOCATOR_SERVICE = NEOFORGE_PKG+".locating.IModLocator";
    
    static {
        ClassLoader loader = MultiVersionModLocator.class.getClassLoader();
        NeoForgeCoreLoader.fixForServiceLayer();
        Object instance = NeoForgeCoreLoader.initCoreAPI(loader);
        if(Objects.isNull(instance))
            throw new RuntimeException("Failed to retrieve CoreAPI instance for MultiVersionModLocator");
        if(loader!=NeoForgeCoreLoader.bootLoader())
            NeoForgeCoreLoader.fixService(MOD_LOCATOR_SERVICE,MOD_LOCATOR_IMPL,loader,true);
    }
    
    private final Object localLocator;
    
    public MultiVersionModLocator() {
        ClassLoader loader = getClass().getClassLoader();
        ClassLoader bootLoader = Launcher.class.getClassLoader();
        TILRef.logInfo("Core NeoForge Locator plugin loaded on {}",loader);
        if(!loader.equals(bootLoader))
            TILRef.logInfo("That's the wrong ClassLoader... Retrieving locator instance from the right "+
                           "ClassLoader {}",bootLoader);
        Object instance = NeoForgeCoreLoader.initCoreAPI(bootLoader);
        
        this.localLocator = Objects.nonNull(instance) ?
                Hacks.invoke(instance,"getModLocator",instance.getClass().getClassLoader()) : null;
        if(Objects.nonNull(this.localLocator)) TILRef.logInfo("Found mod locator {}",this.localLocator.getClass());
        else TILRef.logFatal("Failed to find mod locator! Unable to load multiversion mods");
    }
    
    @Override public void initArguments(Map<String,?> arguments) {
        if(Objects.nonNull(this.localLocator)) {
            ClassLoader loader = getClass().getClassLoader();
            TILDev.logInfo("Initializing mod locator with {}",loader);
            Hacks.invoke(this.localLocator,"initFor",loader,this);
        } else TILRef.logFatal("Locator is null and cannot load multiversion mods! Did it fail to initialize?");
    }
    
    @Override public boolean isValid(IModFile file) {
        return true;
    }
    
    @Override public String name() {
        return "multiversionloader";
    }
    
    @Override public void scanFile(IModFile file, Consumer<Path> pathConsumer) {}
    
    @Override public List<ModFileOrException> scanMods() {
        List<ModFileOrException> files = null;
        if(Objects.nonNull(this.localLocator))
            files = Hacks.invoke(this.localLocator,"scanMods",this.localLocator,this);
        else TILRef.logFatal("Locator is null and cannot scan for multiversion mods! Did it fail to initialize?");
        return Objects.nonNull(files) ? files : Collections.emptyList();
    }
}
