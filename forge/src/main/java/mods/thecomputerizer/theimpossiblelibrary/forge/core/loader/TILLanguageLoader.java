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
            return (T)(java8 ? init.newInstance(info,this.modClass,classLoader,scanResults) :
                    init.newInstance(info,this.modClass,scanResults,extras[0]));
        } catch(Exception ex) {
            TILRef.logError("Failed to get contructor for {}",container);
        }
        return null;
    }
    
    protected <T> T loadModInner(IModInfo info, ClassLoader classLoader, ModFileScanData scanResults,
            Object ... extras) {
        final ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        try {
            final Class<?> container = Class.forName(MOD_CONTAINER,true,contextLoader);
            TILRef.logInfo("Loading FMLModContainer class from {} and got {}",contextLoader,container.getClassLoader());
            String coreName = this.core.getClass().getName();
            
            //Finalizes the module for the class being loaded in the GAME layer
            Methods.invoke(this.scan,"defineClasses",classLoader);
            
            setCoreAPI(Class.forName(coreName,true,classLoader));
            return getInstance(container,info,classLoader,scanResults,extras);
        } catch(Exception ex) {
            throw new RuntimeException("Failed to load "+MOD_CONTAINER+" for multiversion mod!",ex);
        }
    }
    
    protected void setCoreAPI(Class<?> implClass) {
        try {
            implClass.newInstance();
        } catch(Exception ex) {
            TILRef.logError("Failed to set CoreAPI instance...",ex);
        }
    }
}