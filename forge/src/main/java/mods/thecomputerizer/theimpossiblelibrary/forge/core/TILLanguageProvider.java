package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.ForgeModLoading;
import net.minecraftforge.forgespi.language.ILifecycleEvent;
import net.minecraftforge.forgespi.language.IModLanguageProvider;
import net.minecraftforge.forgespi.language.ModFileScanData;
import net.minecraftforge.forgespi.language.ModFileScanData.AnnotationData;
import org.objectweb.asm.Type;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.burningwave.core.assembler.StaticComponentContainer.Constructors;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

public class TILLanguageProvider implements IModLanguageProvider {
    
    static {
        try {
            ClassLoader plugin = TILLanguageProvider.class.getClassLoader();
            ForgeCoreLoader.resyncModules(plugin,"PLUGIN",ForgeCoreLoader.bootLoader());
        } catch(Throwable t) {
            TILRef.logError("Failed to resync modules to BOOT layer",t);
        }
    }
    
    final Object core;
    final Object versionProvider;
    
    public TILLanguageProvider() {
        TILRef.logInfo("Initializing multiversion language provider (Forge edition)");
        ClassLoader pluginLoader = ForgeCoreLoader.layerClassLoader("PLUGIN");
        this.core = ForgeCoreLoader.initCoreAPI(pluginLoader);
        this.versionProvider = Objects.nonNull(this.core) ?
                Methods.invoke(this.core,"getLaunguageProvider") : null;
        if(Objects.nonNull(this.versionProvider))
            TILRef.logInfo("Successfully initialized versioned language provider as {} on {}",this.versionProvider,
                    this.versionProvider.getClass().getClassLoader());
        else TILRef.logError("Initialized versioned language provider as null");
    }
    
    @Override public <R extends ILifecycleEvent<R>> void consumeLifecycleEvent(Supplier<R> ignored)  {}
    
    @Override public Consumer<ModFileScanData> getFileVisitor() {
        final boolean pathBased = ForgeModLoading.isPathBased();
        final Function<AnnotationData,Type> typeMapper = data ->
                Methods.invoke(data,pathBased ? "getAnnotationType" : "annotationType");
        final Function<AnnotationData,Type> classTypeMapper = data ->
                Methods.invoke(data,pathBased ? "getClassType" : "clazz");
        final Function<AnnotationData,Map<String,Object>> dataMapper = data ->
                Methods.invoke(data,pathBased ? "getAnnotationData" : "annotationData");
        final Type modAnnotation = Type.getType("Lnet/minecraftforge/fml/common/Mod;");
        final Class<?> loaderClass = getLanguageLoaderClass();
        final Function<IModLanguageLoader,String> loadModIDMapper =
                loader -> Methods.invoke(loader,"getModid");
        return Objects.isNull(loaderClass) ? scan -> {} :
                scan -> scan.addLanguageLoader(scan.getAnnotations().stream()
                        .filter(ad -> typeMapper.apply(ad).equals(modAnnotation))
                        .map(ad -> {
                            String className = classTypeMapper.apply(ad).getClassName();
                            String value = String.valueOf(dataMapper.apply(ad).get("value"));
                            TILRef.logInfo("Found @Mod class {} with id {}",className,value);
                            return (IModLanguageLoader)Constructors.newInstanceOf(loaderClass,this.core,className,value,scan);
                        }).collect(Collectors.toMap(loadModIDMapper,Function.identity(),(a,b)->a)));
    }
    
    Class<?> getLanguageLoaderClass() {
        Object modLoader = Methods.invoke(this.core,"getModLoader");
        String pkg = "mods.thecomputerizer.theimpossiblelibrary";
        pkg = Methods.invoke(modLoader,"getPackageName",pkg);
        Object version = Methods.invoke(this.core,"getVersion");
        pkg = Methods.invoke(version,"getPackageName",pkg);
        String className = pkg+".core."+("TILLanguageLoader"+version).replace('.','_');
        try {
            return Class.forName(className,true,getClass().getClassLoader());
        } catch(ClassNotFoundException ex) {
            TILRef.logError("Failed to get language loader class {}",className,ex);
        }
        return null;
    }
    
    @Override public String name() {
        return "multiversionprovider";
    }
}