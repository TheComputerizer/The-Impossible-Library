package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import cpw.mods.modlauncher.api.LamdbaExceptionUtils;
import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraftforge.fml.ModLoadingStage;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.IModLanguageProvider.IModLanguageLoader;
import net.minecraftforge.forgespi.language.ModFileScanData;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * Basically the same as FMLJavaModLanguageProvider$FMLModTarget but since it's private, we can't use it...
 * TODO Might not be needed
 */
public class TILLanguageLoader implements IModLanguageLoader {
    
    private static final String LOADING_EXCEPTION = "net.minecraftforge.fml.ModLoadingException";
    private static final String LOADING_STAGE = "net.minecraftforge.fml.ModLoadingStage";
    private static final String MOD_CONTAINER = "net.minecraftforge.fml.javafmlmod.FMLModContainer";
    
    private final String modClass;
    @Getter private final String modid;
    private final TILBetterModScan scan;
    
    public TILLanguageLoader(String modClass, String modid, TILBetterModScan scan) {
        this.modClass = modClass;
        this.modid = modid;
        this.scan = scan;
    }
    
    /**
     * Make all the local variable final like how FML does it.
     */
    @SuppressWarnings("unchecked") @Override
    public <T> T loadMod(IModInfo info, ClassLoader classLoader, ModFileScanData scanResults) {
        final ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        try {
            final Class<?> container = Class.forName(MOD_CONTAINER,true,contextLoader);
            TILDev.logDebug("Loading FMLModContainer class from {} and got {}",contextLoader,container.getClassLoader());
            final Constructor<?> constructor = ReflectionHelper.findConstructor(container,IModInfo.class,String.class,
                    ClassLoader.class,ModFileScanData.class);
            if(Objects.isNull(constructor))
                throw new NoSuchMethodException("Could not find constructor for "+MOD_CONTAINER+"!");
            this.scan.defineClasses(classLoader);
            return (T)constructor.newInstance(info,this.modClass,classLoader,scanResults);
        } catch(InvocationTargetException ex) {
            TILRef.logFatal("Failed to build multiversion mod!",ex);
            final Class<RuntimeException> mle = (Class<RuntimeException>)LamdbaExceptionUtils.uncheck(() ->
                            Class.forName(LOADING_EXCEPTION,true,contextLoader));
            if(mle.isInstance(ex.getTargetException())) throw mle.cast(ex.getTargetException());
            throwLoadException(info,mle,ex);
        } catch(NoSuchMethodException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            TILRef.logFatal("Failed to load {} for multiversion mod!",MOD_CONTAINER,ex);
            final Class<RuntimeException> mle = (Class<RuntimeException>)LamdbaExceptionUtils.uncheck(() ->
                            Class.forName(LOADING_EXCEPTION,true,contextLoader));
            throwLoadException(info,mle,ex);
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    protected final void throwLoadException(IModInfo info, Class<RuntimeException> mle, Exception ex) {
        final ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        final Class<ModLoadingStage> mls = (Class<ModLoadingStage>)LamdbaExceptionUtils.uncheck(() ->
                        Class.forName(LOADING_STAGE,true,contextLoader));
        throw LamdbaExceptionUtils.uncheck(() -> LamdbaExceptionUtils.uncheck(() ->
                        mle.getConstructor(IModInfo.class,mls,String.class,Throwable.class))
                .newInstance(info,Enum.valueOf(mls,"CONSTRUCT"),"fml.modloading.failedtoloadmodclass",ex));
    }
}