package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILForgeModLocator;
import net.minecraftforge.fml.loading.moddiscovery.AbstractJarFileLocator;
import net.minecraftforge.forgespi.locating.IModFile;

import java.util.*;

import static mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader.LOADERS;
import static mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader.locator;

@IndirectCallers
public class MultiversionModLocator extends AbstractJarFileLocator {
    
    static {
        Object instance = CoreAPI.getInstance();
        if(Objects.isNull(instance)) {
            TILRef.logWarn("CoreAPI wasn't loaded before MultiversionModLocator? Rerunning init");
            instance = ForgeCoreLoader.initCoreAPI();
        }
        if(Objects.nonNull(instance)) {
            Class<?> coreClass = instance.getClass();
            ClassLoader loader = coreClass.getClassLoader();
            locator = (TILForgeModLocator)ReflectionHelper.invokeMethod(coreClass,"getModLocator",instance,
                    new Class<?>[]{ClassLoader.class},loader);
        } else TILRef.logError("Failed to initialize CoreAPI instance! Things will probably break now");
    }
    
    public MultiversionModLocator() {
        TILRef.logInfo("Loading plugin loaded with {}",getClass().getClassLoader());
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
        if(Objects.nonNull(locator)) {
            for(ClassLoader loader : LOADERS) locator.initFor(loader,this);
        } else TILRef.logError("Locator is null! Did it fail to initialize?");
    }
}
