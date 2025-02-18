package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import cpw.mods.modlauncher.Launcher;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraftforge.fml.loading.moddiscovery.AbstractJarFileLocator;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.forgespi.locating.IModLocator;

import java.util.*;

public class MultiVersionModLocator extends AbstractJarFileLocator {
    
    static {
        Object instance = ForgeCoreLoader.initCoreAPI(MultiVersionModLocator.class.getClassLoader());
        if(Objects.isNull(instance))
            throw new RuntimeException("Failed to retrieve CoreAPI instance for MultiVersionModLocator");
    }
    
    private final Object localLocator;
    
    public MultiVersionModLocator() {
        ClassLoader loader = getClass().getClassLoader();
        ClassLoader bootLoader = Launcher.class.getClassLoader();
        TILRef.logInfo("Core Forge Locator plugin loaded on {}",loader);
        if(!loader.equals(bootLoader))
            TILRef.logInfo("That's the wrong ClassLoader... Retrieving locator instance from the right "+
                           "ClassLoader {}",bootLoader);
        Object instance = ForgeCoreLoader.initCoreAPI(bootLoader);
        this.localLocator = Objects.nonNull(instance) ? ReflectionHelper.invokeMethod(instance.getClass(),
                "getModLocator",instance,new Class<?>[]{ClassLoader.class},instance.getClass().getClassLoader()) : null;
        if(Objects.nonNull(this.localLocator)) TILRef.logInfo("Found mod locator {}",this.localLocator.getClass());
        else TILRef.logFatal("Failed to find mod locator! Unable to load multiversion mods");
    }
    
    @SuppressWarnings("unchecked")
    @Override public List<IModFile> scanMods() {
        if(Objects.nonNull(this.localLocator))
            return (List<IModFile>)ReflectionHelper.invokeMethod(this.localLocator.getClass(),
                    "scanMods",this.localLocator,new Class<?>[]{IModLocator.class},this);
        TILRef.logFatal("Locator is null and cannot scan for multiversion mods! Did it fail to initialize?");
        return Collections.emptyList();
    }
    
    @Override public String name() {
        return "multiversionloader";
    }
    
    @Override public void initArguments(Map<String,?> arguments) {
        if(Objects.nonNull(this.localLocator)) {
            ClassLoader loader = getClass().getClassLoader();
            TILDev.logInfo("Initializing mod locator with {}",loader);
            ReflectionHelper.invokeMethod(this.localLocator.getClass(),"initFor",this.localLocator,
                    new Class<?>[]{ClassLoader.class,IModLocator.class},loader,this);
        } else TILRef.logFatal("Locator is null and cannot load multiversion mods! Did it fail to initialize?");
    }
}
