package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;

/**
 * Basically the same as FMLJavaModLanguageProvider$FMLModTarget but since it's private, we can't use it...
 */
public abstract class TILLanguageLoader { //TODO Use a proxy to avoid needing different implementation classes for different versions
    
    private static final String CORE_LOADER = "mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader";
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
    
    private <T> T getInstance(Class<?> container, IModInfo info, ClassLoader loader, ModFileScanData scan,
            Object ... extras) {
        try {
            final Object[] args = new Object[]{info,this.modClass,loader,scan};
            if(!ForgeCoreLoader.isJava8()) { args[2] = scan; args[3] = extras[0]; }
            T instance = Hacks.constructAndCast(container,args);
            TILRef.logInfo("Successfully initialized mod container for {}",this.modClass);
            return instance;
        } catch(Throwable t) {
            TILRef.logError("Failed to initialize {} (modClass {})",container,this.modClass,t);
        }
        return null;
    }
    
    protected <T> T loadModInner(IModInfo info, ClassLoader classLoader, ModFileScanData scanResults,
            Object ... extras) {
        try {
            final Class<?> container = Hacks.findClass(MOD_CONTAINER);
            final String coreName = this.core.getClass().getName();
            
            //Finalizes the module for the class being loaded in the GAME layer
            Hacks.invoke(this.scan,"defineClasses",classLoader);
            
            if(!loadedNewCore) setCoreAPI(coreName,classLoader);
            if(extras.length>0) {
                Class<?> coreLoaderGameLayer = Hacks.findClass(CORE_LOADER,classLoader);
                Hacks.invokeStatic(coreLoaderGameLayer,"verifyModule",this.modClass,info,extras[0]);
            }
            return getInstance(container,info,classLoader,scanResults,extras);
        } catch(Throwable t) {
            String msg = "Failed to load "+MOD_CONTAINER+" for multiversion mod!";
            TILRef.logError(msg,t);
            throw new RuntimeException(msg,t);
        }
    }
    
    protected void setCoreAPI(String implName, ClassLoader loader) {
        try {
            Hacks.constructWithLoader(implName,loader);
            loadedNewCore = true;
        } catch(Throwable t) {
            TILRef.logError("Failed to set CoreAPI instance {}",implName,t);
        }
    }
}