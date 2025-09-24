package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.ForgeModLoading;
import net.minecraftforge.forgespi.language.ILifecycleEvent;
import net.minecraftforge.forgespi.language.IModLanguageProvider;
import net.minecraftforge.forgespi.language.ModFileScanData;
import net.minecraftforge.forgespi.language.ModFileScanData.AnnotationData;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TILLanguageProvider implements IModLanguageProvider {
    
    static final Logger LOGGER = TILRef.createLogger("TIL Language Provider (Forge)");
    
    static {
        LOGGER.info("Initialized {}",TILLanguageProvider.class);
    }
    
    Object core;
    
    @Override public <R extends ILifecycleEvent<R>> void consumeLifecycleEvent(Supplier<R> ignored)  {}
    
    @Override public Consumer<ModFileScanData> getFileVisitor() {
        final boolean pathBased = ForgeModLoading.isPathBased();
        final Function<AnnotationData,Type> typeMapper = data ->
                Hacks.invoke(data,pathBased ? "getAnnotationType" : "annotationType");
        final Function<AnnotationData,Type> classTypeMapper = data ->
                Hacks.invoke(data,pathBased ? "getClassType" : "clazz");
        final Function<AnnotationData,Map<String,Object>> dataMapper = data ->
                Hacks.invoke(data,pathBased ? "getAnnotationData" : "annotationData");
        final Type modAnnotation = Type.getType("Lnet/minecraftforge/fml/common/Mod;");
        final Class<IModLanguageLoader> loaderClass = getLanguageLoaderClass();
        final Function<IModLanguageLoader,String> loadModIDMapper =
                loader -> Hacks.invoke(loader,"getModid");
        return Objects.isNull(loaderClass) ? scan -> {} :
                scan -> scan.addLanguageLoader(scan.getAnnotations().stream()
                        .filter(ad -> typeMapper.apply(ad).equals(modAnnotation))
                        .map(ad -> {
                            String className = classTypeMapper.apply(ad).getClassName();
                            String value = String.valueOf(dataMapper.apply(ad).get("value"));
                            TILRef.logInfo("Found @Mod class {} with id {}",className,value);
                            return Hacks.construct(loaderClass,this.core,className,value,scan);
                        }).collect(Collectors.toMap(loadModIDMapper,Function.identity(),(a,b)->a)));
    }
    
    String getFixedClassName(String className) {
        return className.contains("1_19") || className.contains("1_20") || className.contains("1_21") ?
                className.substring(0,className.length()-2) : className;
    }
    
    String getFixedPkg(String pkg) {
        return pkg.contains("v19") || pkg.contains("v20") || pkg.contains("v21") ?
                pkg.substring(0,pkg.length()-3) : pkg;
    }
    
    <T> Class<T> getLanguageLoaderClass() {
        initCoreAPI();
        Object modLoader = Hacks.invoke(this.core,"getModLoader");
        String pkg = "mods.thecomputerizer.theimpossiblelibrary";
        pkg = Hacks.invoke(modLoader,"getPackageName",pkg);
        Object version = Hacks.invoke(this.core,"getVersion");
        pkg = Hacks.invoke(version,"getPackageName",pkg);
        if(Objects.isNull(pkg)) {
            LOGGER.error("Failed to find language loader package");
            return null;
        }
        String className = getFixedClassName(("TILLanguageLoader"+version).replace('.','_'));
        className = getFixedPkg(pkg)+".core.loader."+className;
        try {
            return Hacks.findClass(className,getClass().getClassLoader(),true);
        } catch(Throwable t) {
            TILRef.logError("Failed to get language loader class {}",className,t);
        }
        return null;
    }
    
    void initCoreAPI() {
        if(Objects.nonNull(this.core)) return;
        ClassLoader pluginLoader = ForgeCoreLoader.isJava8() ? getClass().getClassLoader() :
                ForgeCoreLoader.layerClassLoader("PLUGIN");
        this.core = ForgeCoreLoader.initCoreAPI(pluginLoader);
        LOGGER.info("Retrieved CoreAPI instance as {}",this.core);
    }
    
    @Override public String name() {
        return "multiversionprovider";
    }
}