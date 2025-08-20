package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.BiConsumer;

import static java.lang.System.err;
import static java.lang.System.out;
import static mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader.SECURE_CLASSLOADER_FORMAT;
import static org.burningwave.core.assembler.StaticComponentContainer.Constructors;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

public interface ModuleSystemAccessor {
    
    String LAYER_ENUM_CLASS = "cpw.mods.modlauncher.api.IModuleLayerManager$Layer";
    String MODULE_CLASS = "java.lang.Module";
    String MODULE_DESCRIPTOR_CLASS = "java.lang.module.ModuleDescriptor";
    String MODULE_FINDER_EXTENSION_CLASS = SECURE_CLASSLOADER_FORMAT ?
            "net.minecraftforge.securemodules.SecureModuleFinder" : "cpw.mods.cl.JarModuleFinder";
    String RESOLVED_MODULE_CLASS = "java.lang.module.ResolvedModule";
    
    static String changing(String secure, String old) {
        return SECURE_CLASSLOADER_FORMAT ? secure : old;
    }
    
    static <T> T construct(Object accessorOrLogger, String targetName, Object ... args) {
        return construct(accessorOrLogger,getClassForName(targetName,accessorOrLogger),args);
    }
    
    static <T> T construct(Object accessorOrLogger, Class<?> target, Object ... args) {
        return construct(accessorOrLogger,target,false,args);
    }
    
    static <T> T construct(Object accessorOrLogger, Class<?> target, boolean direct, Object ... args) {
        if(Objects.nonNull(target))
            return direct ? Constructors.newInstanceDirectOf(target,args) : Constructors.newInstanceOf(target,args);
        String msg = "Cannot contruct null class! args = "+Arrays.toString(args);
        Logger logger = getAsLogger(accessorOrLogger);
        if(Objects.nonNull(logger)) logger.error(msg);
        else err.println(msg);
        return null;
    }
    
    static <T> T constructDirect(Object accessorOrLogger, Class<?> target, Object ... args) {
        return construct(accessorOrLogger,target,true,args);
    }
    
    @IndirectCallers
    static <T> T constructDirect(Object accessorOrLogger, String targetName, Object ... args) {
        return constructDirect(accessorOrLogger,getClassForName(targetName,accessorOrLogger),args);
    }
    
    static void exportAllPackages(Object accessorOrLogger, String ... layerNames) {
        for(String layer : layerNames) getModuleLayer(layer,accessorOrLogger).exportPackagesToAll();
    }
    
    static Map<String,Collection<String>> getAllModuleNames(Object accessorOrLogger, String ... layerNames) {
        Map<String,Collection<String>> map = new HashMap<>();
        ModuleLayerHandlerAccess handler = getModuleLayerHandler(accessorOrLogger);
        for(Entry<ModuleLayerAccess,String> layerEntry : handler.getAllNamedModuleLayers(layerNames).entrySet())
            map.put(layerEntry.getValue(),layerEntry.getKey().moduleNames());
        return map;
    }
    
    static Logger getAsLogger(Object accessorOrLogger) {
        if(accessorOrLogger instanceof ModuleSystemAccessor) return ((ModuleSystemAccessor)accessorOrLogger).logger();
        if(accessorOrLogger instanceof Logger) return (Logger)accessorOrLogger;
        return null;
    }
    
    static ClassAccess getClassAccess(String className, boolean intialize, ClassLoader loader,
            Object accessorOrLogger) {
        return getClassAccess(getClassForName(className,intialize,loader,accessorOrLogger),accessorOrLogger);
    }
    
    /**
     * We don't want to accidentally override or throw any errors related to getClass
     */
    static ClassAccess getClassAccess(Class<?> clazz, Object accessorOrLogger) {
        return Objects.nonNull(clazz) ? new ClassAccess(clazz,accessorOrLogger) : null;
    }
    
    static Class<?> getClassForName(String className, Object accessorOrLogger) {
        try {
            return Class.forName(className);
        } catch(Throwable t) {
            String msg = "Failed to get class for "+className;
            Logger logger = getAsLogger(accessorOrLogger);
            if(Objects.nonNull(logger)) logger.error(msg,t);
            else {
                err.println(msg);
                t.printStackTrace(err);
            }
        }
        return null;
    }
    
    static Class<?> getClassForName(String className, boolean initialize, ClassLoader loader,
            Object accessorOrLogger) {
        try {
            return Class.forName(className,initialize,loader);
        } catch(Throwable t) {
            String msg = "Failed to get class for "+className;
            Logger logger = getAsLogger(accessorOrLogger);
            if(Objects.nonNull(logger)) logger.error(msg,t);
            else {
                err.println(msg);
                t.printStackTrace(err);
            }
        }
        return null;
    }
    
    static ClassLoaderAccess getClassLoader(ClassLoader loader, Object accessorOrLogger) {
        return new ClassLoaderAccess(loader,accessorOrLogger);
    }
    
    static ConfigurationAccess getConfiguration(Object configuration, Object accessorOrLogger) {
        return new ConfigurationAccess(configuration,accessorOrLogger);
    }
    
    static EnvironmentAccess getEnvironment(Object accessorOrLogger) {
        return getLauncher(accessorOrLogger).environment();
    }
    
    static EnvironmentAccess getEnvironment(Object environment, Object accessorOrLogger) {
        return new EnvironmentAccess(environment,accessorOrLogger);
    }
    
    static JarMetadataAccess getJarMetadata(Object jarMetadata, Object accessorOrLogger) {
        return new JarMetadataAccess(jarMetadata,accessorOrLogger);
    }
    
    static JavaLangAccess getJavaLangAccess(Object accessorOrLogger) {
        Object access = getStaticDirect(ServiceLoader.class,"LANG_ACCESS",accessorOrLogger);
        return getJavaLangAccess(access,accessorOrLogger);
    }
    
    static JavaLangAccess getJavaLangAccess(Object langAccess, Object accessorOrLogger) {
        return new JavaLangAccess(langAccess,accessorOrLogger);
    }
    
    static LauncherAccess getLauncher(Object accessorOrLogger) {
        return new LauncherAccess(accessorOrLogger);
    }
    
    static ClassLoader getLayerClassLoader(String layerName, Object accessorOrLogger) {
        return getLayerInfo(layerName,accessorOrLogger).getClassLoader();
    }
    
    static Enum<?> getLayerEnum(String layerName) {
        return ForgeCoreLoader.getEnum(ForgeCoreLoader.bootLoader(),LAYER_ENUM_CLASS,layerName);
    }
    
    static LayerInfoAccess getLayerInfo(String layerName, Object accessorOrLogger) {
        LayerInfoAccess layerInfo = getLayerInfo(getLayerEnum(layerName),accessorOrLogger);
        layerInfo.setLayerName(layerName);
        return layerInfo;
    }
    
    static LayerInfoAccess getLayerInfo(Enum<?> layerEnum, Object accessorOrLogger) {
        return getModuleLayerHandler(accessorOrLogger).getLayerInfo(layerEnum);
    }
    
    static LayerInfoAccess getLayerInfo(Object layerInfo, Object accessorOrLogger) {
        return new LayerInfoAccess(layerInfo,accessorOrLogger);
    }
    
    static ModuleClassLoaderAccess getLayerModuleClassLoader(String layerName, Object accessorOrLogger) {
        return getLayerInfo(layerName,accessorOrLogger).getModuleClassLoader();
    }
    
    static ModFileAccess getModFile(Object modFile, Object accessorOrLogger) {
        return new ModFileAccess(modFile,accessorOrLogger);
    }
    
    static ModFileInfoAccess getModFileInfo(Object modFileInfo, Object accessorOrLogger) {
        return new ModFileInfoAccess(modFileInfo,accessorOrLogger);
    }
    
    static ModuleAccess getModule(Object module, Object accessorOrLogger) {
        return new ModuleAccess(module,accessorOrLogger);
    }
    
    static ModuleClassLoaderAccess getModuleClassLoader(String layerName, Object accessorOrLogger) {
        return getLayerInfo(layerName,accessorOrLogger).getModuleClassLoader();
    }
    
    static ModuleClassLoaderAccess getModuleClassLoader(ClassLoader loader, Object accessorOrLogger) {
        return new ModuleClassLoaderAccess(loader,accessorOrLogger);
    }
    
    static ModuleDescriptorAccess getModuleDescriptor(Object moduleDescriptor, Object accessorOrLogger) {
        return new ModuleDescriptorAccess(moduleDescriptor,accessorOrLogger);
    }
    
    static ModuleDescriptorBuilderAccess getModuleDescriptorBuilder(String moduleName, Object accessorOrLogger) {
        return invokeStatic(accessorOrLogger,MODULE_DESCRIPTOR_CLASS,"newAutomaticModule",moduleName);
    }
    
    static ModuleDescriptorBuilderAccess getModuleDescriptorBuilder(Object builder, Object accessorOrLogger) {
        return new ModuleDescriptorBuilderAccess(builder,accessorOrLogger);
    }
    
    static ModuleFinderAccess getModuleFinder(Object moduleFinder, Object accessorOrLogger) {
        return new ModuleFinderAccess(moduleFinder,accessorOrLogger);
    }
    
    static ModuleLayerAccess getModuleLayer(String layerName, Object accessorOrLogger) {
        return getModuleLayerHandler(accessorOrLogger).getModuleLayer(layerName);
    }
    
    static ModuleLayerAccess getModuleLayer(Object moduleLayer, Object accessorOrLogger) {
        return new ModuleLayerAccess(moduleLayer,accessorOrLogger);
    }
    
    static ModuleLayerHandlerAccess getModuleLayerHandler(Object accessorOrLogger) {
        return getEnvironment(accessorOrLogger).getModuleLayerHandler();
    }
    
    static ModuleLayerHandlerAccess getModuleLayerHandler(Object moduleLayerHandler, Object accessorOrLogger) {
        return new ModuleLayerHandlerAccess(moduleLayerHandler,accessorOrLogger);
    }
    
    static ModuleReferenceAccess getModuleReference(Object moduleReference, Object accessorOrLogger) {
        return new ModuleReferenceAccess(moduleReference,accessorOrLogger);
    }
    
    static ResolvedModuleAccess getResolvedModule(Object resolvedModule, Object accessorOrLogger) {
        return new ResolvedModuleAccess(resolvedModule,accessorOrLogger);
    }
    
    static SecureJarAccess getSecureJar(Object secureJar, Object accessorOrLogger) {
        return new SecureJarAccess(secureJar,accessorOrLogger);
    }
    
    static SecureJarProviderAccess getSecureJarProvider(Object provider, Object accessorOrLogger) {
        return new SecureJarProviderAccess(provider,accessorOrLogger);
    }
    
    @IndirectCallers
    static <T> T getStatic(String className, String name, Object accessorOrLogger) {
        return getStatic(className,name,false,accessorOrLogger);
    }
    
    @IndirectCallers
    static <T> T getStatic(Class<?> target, String name, Object accessorOrLogger) {
        return getStatic(target,name,false,accessorOrLogger);
    }
    
    static <T> T getStatic(String className, String name, boolean direct, Object accessorOrLogger) {
        return getStatic(getClassForName(className,accessorOrLogger),name,direct,accessorOrLogger);
    }
    
    static <T> T getStatic(Class<?> target, String name, boolean direct, Object accessorOrLogger) {
        if(Objects.nonNull(target))
            return direct ? Fields.getStaticDirect(target,name) : Fields.getStatic(target,name);
        String msg = "Cannot get static field "+name+" on null target class!";
        Logger logger = getAsLogger(accessorOrLogger);
        if(Objects.nonNull(logger)) logger.error(msg);
        else err.println(msg);
        return null;
    }
    
    @IndirectCallers
    static <T> T getStaticDirect(String className, String name, Object accessorOrLogger) {
        return getStatic(className,name,true,accessorOrLogger);
    }
    
    static <T> T getStaticDirect(Class<?> target, String name, Object accessorOrLogger) {
        return getStatic(target,name,true,accessorOrLogger);
    }
    
    static <T> T invokeStatic(Object accessorOrLogger, String targetName, String methodName, Object ... args) {
        return invokeStatic(accessorOrLogger,targetName,methodName,false,args);
    }
    
    static <T> T invokeStatic(Object accessorOrLogger, Class<?> target, String methodName, Object ... args) {
        return invokeStatic(accessorOrLogger,target,methodName,false,args);
    }
    
    static <T> T invokeStatic(Object accessorOrLogger, String targetName, String methodName, boolean direct,
            Object ... args) {
        return invokeStatic(accessorOrLogger,getClassForName(targetName,accessorOrLogger),methodName,direct,args);
    }
    
    static <T> T invokeStatic(Object accessorOrLogger, Class<?> target, String methodName, boolean direct,
            Object ... args) {
        if(Objects.nonNull(target))
            return direct ? Methods.invokeStaticDirect(target,methodName,args) :
                    Methods.invokeStatic(target,methodName,args);
        String msg = "Cannot invoke static method "+methodName+" on null target class!";
        Logger logger = getAsLogger(accessorOrLogger);
        if(Objects.nonNull(logger)) logger.error(msg);
        else err.println(msg);
        return null;
    }
    
    static <T> T invokeStaticDirect(Object accessorOrLogger, Class<?> target, String methodName, Object ... args) {
        return invokeStatic(accessorOrLogger,target,methodName,true,args);
    }
    
    static <T> T invokeStaticDirect(Object accessorOrLogger, String targetClass, String methodName, Object ... args) {
        return invokeStatic(accessorOrLogger,targetClass,methodName,true,args);
    }
    
    static ModuleAccess newModule(Object accessorOrLogger, Object layer, ClassLoader loader, Object descriptor,
            URI uri) {
        return getModule(construct(accessorOrLogger,MODULE_CLASS,layer,loader,descriptor,uri),accessorOrLogger);
    }
    
    static ResolvedModuleAccess newResolvedModule(Object accessorOrLogger, Object configuration,
            Object moduleReference) {
        return getResolvedModule(construct(accessorOrLogger,RESOLVED_MODULE_CLASS,configuration,moduleReference),accessorOrLogger);
    }
    
    Object access();
    
    @SuppressWarnings("unchecked")
    default <T> T as(Object o) {
        return (T)o;
    }
    
    default <T> T as(Object o, T ifNull) {
        return Objects.nonNull(o) ? as(o) : ifNull;
    }
    
    @IndirectCallers
    default <T> Collection<T> asCollection(Object object) {
        return as(object,Collections.emptySet());
    }
    
    @IndirectCallers
    default <T> List<T> asList(Object object) {
        return as(object,Collections.emptyList());
    }
    
    @IndirectCallers
    default <K,V> Map<K,V> asMap(Object object) {
        return as(object,Collections.emptyMap());
    }
    
    @IndirectCallers
    default Map<String,Object> asMapDefault(Object object) {
        return as(object,Collections.emptyMap());
    }
    
    @IndirectCallers
    default <K,V> Map<K,Collection<V>> asMapCollectionValue(Object object) {
        return as(object,Collections.emptyMap());
    }
    
    @IndirectCallers
    default <T> Optional<T> asOptional(Object object) {
        return as(object,Optional.empty());
    }
    
    @IndirectCallers
    default <T> T asOptionalResult(Object object) {
        return asOptionalResult(object,null);
    }
    
    default <T> T asOptionalResult(Object object, T orElse) {
        Optional<T> optional = asOptional(object);
        return optional.orElse(orElse);
    }
    
    @IndirectCallers
    default <T> Set<T> asSet(Object object) {
        return as(object,Collections.emptySet());
    }
    
    default <T> T get(String name) {
        return get(name,false);
    }
    
    default <T> T get(String name, boolean direct) {
        Object obj = access();
        if(Objects.nonNull(obj)) return direct ? Fields.getDirect(obj,name) : Fields.get(obj,name);
        logOrPrintError("Cannot get field "+name+"! (return value of access() was null)");
        return null;
    }
    
    @IndirectCallers
    default <T> T getDirect(String name) {
        return get(name,true);
    }
    
    default <T> T invoke(String name, Object ... args) {
        return invoke(name,false,args);
    }
    
    default <T> T invoke(String name, boolean direct, Object ... args) {
        Object obj = access();
        if(Objects.nonNull(obj)) return direct ? Methods.invokeDirect(obj,name,args) : Methods.invoke(obj,name,args);
        logger().error("Cannot invoke method {}! (return value of access() was null)",name);
        return null;
    }
    
    @IndirectCallers
    default <T> T invokeDirect(String name, Object ... args) {
        return invoke(name,true,args);
    }
    
    @IndirectCallers
    default void logOrPrint(String msg, BiConsumer<Logger,String> log) {
        logOrPrint(msg,false,log);
    }
    
    default void logOrPrint(String msg, boolean isError, BiConsumer<Logger,String> log) {
        Logger logger = logger();
        if(Objects.nonNull(logger)) {
            if(Objects.nonNull(log)) log.accept(logger,msg);
            else logger.info("[MISSING LOG FUNCTION] {}",msg);
        } else {
            if(isError) err.println(msg);
            else out.println(msg);
        }
    }
    
    @IndirectCallers
    default void logOrPrintError(String msg) {
        logOrPrint(msg,true,Logger::error);
    }
    
    Logger logger();
    
    default void set(String name, Object value) {
        set(name,value,false);
    }
    
    default void set(String name, Object value, boolean direct) {
        Object obj = access();
        if(Objects.nonNull(obj)) {
            if(direct) Fields.setDirect(obj,name,value);
            else Fields.set(obj,name,value);
            return;
        }
        logOrPrintError("Cannot set field "+name+" to "+value+"! (return value of access() was null)");
    }
    
    @IndirectCallers
    default void setDirect(String name, Object value) {
        set(name,value,true);
    }
}