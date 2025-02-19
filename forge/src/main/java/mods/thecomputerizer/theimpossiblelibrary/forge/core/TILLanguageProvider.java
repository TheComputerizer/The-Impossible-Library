package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import cpw.mods.modlauncher.Launcher;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILForgeLanguageProvider;
import net.minecraftforge.forgespi.language.ILifecycleEvent;
import net.minecraftforge.forgespi.language.IModLanguageProvider;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.burningwave.core.assembler.StaticComponentContainer.ClassLoaders;
import static org.burningwave.core.assembler.StaticComponentContainer.Constructors;

public class TILLanguageProvider implements IModLanguageProvider {
    
    private static final String CORE_NAME = "mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader";
    private static final Logger LOGGER = LoggerFactory.getLogger("TIL Language Provider");
    
    //Reflection is needed since this module is forced to be loaded in the PLUGIN layer.
    //We need to fix the modules from the BOOT layer
    static {
        LOGGER.error("OH HELLO THERE");
        try {
            ClassLoader bootLoader = Launcher.class.getClassLoader();
            Class<?> coreClass = Class.forName(CORE_NAME,false,bootLoader);
            Method method = coreClass.getDeclaredMethod("resyncModules",ClassLoader.class,String.class);
            method.invoke(null,TILLanguageProvider.class.getClassLoader(),"PLUGIN");
        } catch(ClassNotFoundException|NoSuchMethodException|IllegalAccessException|InvocationTargetException ex) {
            LOGGER.error("Failed to resync modules to BOOT layer",ex);
        }
    }
    
    static CoreAPI findCoreAPI() {
        Object instance = CoreAPI.getInstance();
        if(Objects.isNull(instance)) instance = ForgeCoreLoader.initCoreAPI(CoreAPI.class.getClassLoader());
        TILDev.logInfo("Found CoreAPI? {}",instance);
        return (CoreAPI)instance;
    }
    
    static Object findVersionProvider() {
        ClassLoader pluginLoader = ForgeCoreLoader.layerClassLoader("PLUGIN");
        Class<?> target = findCoreAPI().getLaunguageProvider().getClass();
        try {
            return Constructors.newInstanceOf(ClassLoaders.loadOrDefine(target,pluginLoader));
        } catch(Exception ex) {
            TILRef.logError("Failed to find version provider {} on {}",target,pluginLoader,ex);
        }
        return null;
    }
    
    final TILForgeLanguageProvider versionProvider;
    
    public TILLanguageProvider() {
        this.versionProvider = (TILForgeLanguageProvider)findVersionProvider();
        if(Objects.nonNull(this.versionProvider))
            TILRef.logInfo("Successfully initialized versioned language provider on {}",this.versionProvider.getClass().getClassLoader());
        else TILRef.logError("Initialized versioned language provider as null");
    }
    
    @Override public <R extends ILifecycleEvent<R>> void consumeLifecycleEvent(Supplier<R> consumeEvent) {}
    
    @Override public Consumer<ModFileScanData> getFileVisitor() {
        if(Objects.nonNull(versionProvider)) return versionProvider.getFileVisitor(this);
        TILRef.logError("Version specific language provider not found! Did it fail to load?");
        return scan -> {};
    }
    
    @Override public String name() {
        return "multiversionprovider";
    }
}