package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILForgeModLocator;
import net.minecraftforge.fml.loading.moddiscovery.AbstractJarFileLocator;
import net.minecraftforge.forgespi.locating.IModFile;

import java.util.*;

import static mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader.coreLoader;
import static mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader.locator;

@IndirectCallers
public class MultiversionModLocator extends AbstractJarFileLocator {
    
    static {
        Object instance = ForgeCoreLoader.initCoreAPI(CoreAPI.class.getClassLoader());
        if(Objects.isNull(instance))
            throw new RuntimeException("Failed to retrieve CoreAPI instance for MultiversionModLocator");
        Class<?> coreClass = instance.getClass();
        locator = (TILForgeModLocator)ReflectionHelper.invokeMethod(coreClass,"getModLocator",instance,
                new Class<?>[]{ClassLoader.class}, coreClass.getClassLoader());
    }
    
    public MultiversionModLocator() {
        TILRef.logInfo("Core Forge Locator plugin loaded on {}",getClass().getClassLoader());
    }
    
    @Override public List<IModFile> scanMods() {
        if(Objects.nonNull(locator)) return locator.scanMods(this);
        TILRef.logError("Locator is null! Did it fail to initialize?");
        return Collections.emptyList();
    }
    
    @Override public String name() {
        return "multiversionloader";
    }
    
    @Override public void initArguments(Map<String,?> arguments) {
        if(Objects.nonNull(locator)) locator.initFor(coreLoader,this);
        else TILRef.logError("Locator is null! Did it fail to initialize?");
    }
}
