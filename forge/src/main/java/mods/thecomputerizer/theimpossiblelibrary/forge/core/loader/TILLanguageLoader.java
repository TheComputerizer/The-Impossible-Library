package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import cpw.mods.modlauncher.api.LamdbaExceptionUtils;
import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraftforge.fml.ModLoadingStage;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * Basically the same as FMLJavaModLanguageProvider$FMLModTarget but since it's private, we can't use it...
 */
public abstract class TILLanguageLoader {
    
    private static final String LOADING_EXCEPTION = "net.minecraftforge.fml.ModLoadingException";
    private static final String LOADING_STAGE = "net.minecraftforge.fml.ModLoadingStage";
    private static final String MOD_CONTAINER = "net.minecraftforge.fml.javafmlmod.FMLModContainer";
    
    protected final String modClass;
    @Getter protected final String modid;
    protected final ModFileScanData scan;
    
    protected TILLanguageLoader(String modClass, String modid, ModFileScanData scan) {
        this.modClass = modClass;
        this.modid = modid;
        this.scan = scan;
    }
    
    /**
     * Due to class loading conflicts, ModFileScanData cannot be directly cast to TILBetterModScan.
     * Reflection is the only way...
     */
    protected void defineClasses(ClassLoader loader) {
        ReflectionHelper.invokeMethod(this.scan.getClass(),"defineClasses",this.scan,new Class<?>[]{
                ClassLoader[].class},(Object)new ClassLoader[]{loader});
        String unparsedCore = CoreAPI.getInstance(this.scan.getClass().getClassLoader()).toString().split(" ")[0];
        try {
            Class<?> coreClass = loader.loadClass(unparsedCore);
            ReflectionHelper.invokeStaticMethod(coreClass.getSuperclass(),"setInstance",new Class<?>[]{
                    Class.class},coreClass);
        } catch(ClassNotFoundException ex) {
            TILRef.logInfo("Couldn't actually load CoreAPI to {}",loader,ex);
        }
    }
    
    /**
     * Make all the local variables final like how FML does it.
     */
    @SuppressWarnings("unchecked") protected  <T> T loadModInner(IModInfo info, ClassLoader classLoader, ModFileScanData scanResults) {
        final ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        try {
            final Class<?> container = Class.forName(MOD_CONTAINER,true,contextLoader);
            TILRef.logInfo("Loading FMLModContainer class from {} and got {}",contextLoader,container.getClassLoader());
            final Constructor<?> constructor = ReflectionHelper.findConstructor(container,IModInfo.class,String.class,
                    ClassLoader.class,ModFileScanData.class);
            if(Objects.isNull(constructor))
                throw new NoSuchMethodException("Could not find constructor for "+MOD_CONTAINER+"!");
            defineClasses(classLoader);
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
    protected final void throwLoadException(Object info, Class<RuntimeException> mle, Exception ex) {
        final ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        final Class<ModLoadingStage> mls = (Class<ModLoadingStage>)LamdbaExceptionUtils.uncheck(() ->
                        Class.forName(LOADING_STAGE,true,contextLoader));
        throw LamdbaExceptionUtils.uncheck(() -> LamdbaExceptionUtils.uncheck(() ->
                        mle.getConstructor(info.getClass(),mls,String.class,Throwable.class))
                .newInstance(info,Enum.valueOf(mls,"CONSTRUCT"),"fml.modloading.failedtoloadmodclass",ex));
    }
}