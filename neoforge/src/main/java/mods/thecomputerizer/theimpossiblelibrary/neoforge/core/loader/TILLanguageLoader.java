package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.NeoForgeCoreLoader;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.language.ModFileScanData;

import java.lang.reflect.Constructor;

import static org.burningwave.core.assembler.StaticComponentContainer.Constructors;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

public abstract class TILLanguageLoader {
    
    private static final String MOD_CONTAINER = "net.neoforged.fml.javafmlmod.FMLModContainer";
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
    private <T> T getInstance(Class<?> container, IModInfo info, ModFileScanData scanResults,
            ModuleLayer layer) {
        try {
            Constructor<?> init = container.getConstructor(IModInfo.class,String.class,ModFileScanData.class,
                                                           ModuleLayer.class);
            T instance = (T)init.newInstance(info,this.modClass,scanResults,layer);
            TILRef.logInfo("Successfully initialized mod container for {}",this.modClass);
            return instance;
        } catch(Throwable t) {
            TILRef.logError("Failed to initialize {} (modClass {})",container,this.modClass,t);
        }
        return null;
    }
    
    protected <T> T loadModInner(IModInfo info, ClassLoader classLoader, ModFileScanData scanResults,
            ModuleLayer layer) {
        try {
            final String coreName = this.core.getClass().getName();
            
            //Finalizes the module for the class being loaded in the GAME layer
            Methods.invoke(this.scan,"defineClasses",classLoader);
            
            if(!loadedNewCore) setCoreAPI(Class.forName(coreName,true,classLoader));
            NeoForgeCoreLoader.verifyModule(this.modClass,info,layer);
            return getInstance(Hacks.findClass(MOD_CONTAINER,true),info,scanResults,layer);
        } catch(Throwable t) {
            final String msg = "Failed to load "+MOD_CONTAINER+" for multiversion mod!";
            TILRef.logError(msg,t);
            throw new RuntimeException(msg,t);
        }
    }
    
    protected void setCoreAPI(Class<?> implClass) {
        try {
            Hacks.checkBurningWaveInit();
            Constructors.newInstanceOf(implClass);
            loadedNewCore = true;
        } catch(Throwable t) {
            TILRef.logError("Failed to set CoreAPI instance {}",implClass,t);
        }
    }
}