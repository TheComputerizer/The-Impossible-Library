package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;

import static org.burningwave.core.assembler.StaticComponentContainer.Constructors;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

/**
 * Basically the same as FMLJavaModLanguageProvider$FMLModTarget but since it's private, we can't use it...
 */
public abstract class TILLanguageLoader {
    
    private static final String MOD_CONTAINER = "net.minecraftforge.fml.javafmlmod.FMLModContainer";
    
    protected final String modClass;
    @Getter protected final String modid;
    protected final ModFileScanData scan;
    
    protected TILLanguageLoader(String modClass, String modid, ModFileScanData scan) {
        this.modClass = modClass;
        this.modid = modid;
        this.scan = scan;
    }
    
    private <T> T getInstance(Class<?> container, IModInfo info, ClassLoader classLoader, ModFileScanData scanResults,
            Object ... extras) {
        return ForgeCoreLoader.isJava8() ?
                Constructors.newInstanceOf(container,info,this.modClass,classLoader,scanResults) :
                Constructors.newInstanceOf(container,info,this.modClass,scanResults,extras[0]);
        
    }
    
    /**
     * Make all the local variables final like how FML does it.
     */
    protected  <T> T loadModInner(IModInfo info, ClassLoader classLoader, ModFileScanData scanResults,
            Object ... extras) {
        final ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        try {
            final Class<?> container = Class.forName(MOD_CONTAINER,true,contextLoader);
            TILRef.logInfo("Loading FMLModContainer class from {} and got {}",contextLoader,container.getClassLoader());
            Methods.invoke(this.scan,"defineClasses",ForgeCoreLoader.bootLoader());
            return getInstance(container,info,classLoader,scanResults,extras);
        } catch(Exception ex) {
            throw new RuntimeException("Failed to load "+MOD_CONTAINER+" for multiversion mod!",ex);
        }
    }
}