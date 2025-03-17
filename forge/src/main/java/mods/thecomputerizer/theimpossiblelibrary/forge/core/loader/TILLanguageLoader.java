package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;

import java.lang.reflect.Constructor;

import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

/**
 * Basically the same as FMLJavaModLanguageProvider$FMLModTarget but since it's private, we can't use it...
 */
public abstract class TILLanguageLoader {
    
    private static final String MOD_CONTAINER = "net.minecraftforge.fml.javafmlmod.FMLModContainer";
    static boolean loadedNewCore;
    
    protected final CoreAPI core;
    protected final String modClass;
    @Getter protected final String modid;
    protected final ModFileScanData scan;
    
    protected TILLanguageLoader(CoreAPI core, String modClass, String modid, ModFileScanData scan) {
        this.core = core;
        this.modClass = modClass;
        this.modid = modid;
        this.scan = scan;
    }
    
    @SuppressWarnings("unchecked")
    private <T> T getInstance(Class<?> container, IModInfo info, ClassLoader classLoader, ModFileScanData scanResults,
            Object ... extras) {
        try {
            boolean java8 = ForgeCoreLoader.isJava8();
            Constructor<?> init = java8 ? container.getConstructor(
                    IModInfo.class,String.class,ClassLoader.class,ModFileScanData.class) : container.getConstructor(
                            IModInfo.class,String.class,ModFileScanData.class,extras[0].getClass());
            T instance = (T)(java8 ? init.newInstance(info,this.modClass,classLoader,scanResults) :
                    init.newInstance(info,this.modClass,scanResults,extras[0]));
            TILRef.logInfo("Successfully initialized mod container for {}",this.modClass);
            return instance;
        } catch(Throwable t) {
            TILRef.logError("Failed to initialize {} (modClass {})",container,this.modClass,t);
        }
        return null;
    }
    
    protected <T> T loadModInner(IModInfo info, ClassLoader classLoader, ModFileScanData scanResults,
            Object ... extras) {
        final ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        try {
            final Class<?> container = Class.forName(MOD_CONTAINER,true,contextLoader);
            String coreName = this.core.getClass().getName();
            
            //Finalizes the module for the class being loaded in the GAME layer
            Methods.invoke(this.scan,"defineClasses",classLoader);
            
            if(!loadedNewCore) setCoreAPI(Class.forName(coreName,true,classLoader));
            ForgeCoreLoader.verifyModule(this.modClass,info,extras[0]);
            return getInstance(container,info,classLoader,scanResults,extras);
        } catch(Throwable t) {
            String msg = "Failed to load "+MOD_CONTAINER+" for multiversion mod!";
            TILRef.logError(msg,t);
            throw new RuntimeException(msg,t);
        }
    }
    
    protected void setCoreAPI(Class<?> implClass) {
        try {
            implClass.newInstance();
            loadedNewCore = true;
        } catch(Throwable t) {
            TILRef.logError("Failed to set CoreAPI instance {}",implClass,t);
        }
    }
}