package mods.thecomputerizer.theimpossiblelibrary.api.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

public abstract class AbstractModuleSystemAccessor implements ModuleSystemAccessor {
    
    protected final Object access;
    protected final Logger logger;
    
    protected AbstractModuleSystemAccessor(Object access, Object accessorOrLogger) {
        this.access = access;
        if(accessorOrLogger instanceof ModuleSystemAccessor)
            this.logger = ((ModuleSystemAccessor)accessorOrLogger).logger();
        else if(accessorOrLogger instanceof Logger) this.logger = (Logger)accessorOrLogger;
        else {
            this.logger = null;
            logOrPrintError("Initialized Logger as null! input = "+accessorOrLogger);
        }
    }
    
    @Override public Object access() {
        return this.access;
    }
    
    @IndirectCallers
    protected <T> T construct(String targetName, Object ... args) {
        return ModuleSystemAccessor.construct(this,targetName,args);
    }
    
    @IndirectCallers
    protected <T> T construct(Class<?> target, Object ... args) {
        return ModuleSystemAccessor.construct(this,target,args);
    }
    
    @IndirectCallers
    protected <T> T construct(Class<?> target, boolean direct, Object ... args) {
        return ModuleSystemAccessor.construct(this,target,direct,args);
    }
    
    @IndirectCallers
    protected <T> T constructDirect(Class<?> target, Object ... args) {
        return ModuleSystemAccessor.construct(this,target,args);
    }
    
    @IndirectCallers
    protected <T> T constructDirect(String targetName, Object ... args) {
        return ModuleSystemAccessor.construct(this,targetName,args);
    }
    
    protected Consumer<Map<?,?>> defaultMapPrinter() {
        return map -> {
            for(Entry<?,?> entry : map.entrySet()) {
                logOrPrint("\t"+entry.getKey(),Logger::debug);
                Object valueObj = entry.getValue();
                if(valueObj instanceof Collection<?>) {
                    for(Object value : (Collection<?>)valueObj) logOrPrint("\t\t"+value,Logger::debug);
                } else logOrPrint("\t\t"+valueObj,Logger::debug);
            }
        };
    }
    
    /**
     * We don't want to accidentally override or throw any errors related to getClass
     */
    @IndirectCallers
    protected ClassAccess getClassAccess(Class<?> clazz) {
        return ModuleSystemAccessor.getClassAccess(clazz,this);
    }
    
    @IndirectCallers
    protected ClassAccess getClassAccess(String className, boolean intialize, ClassLoader loader) {
        return ModuleSystemAccessor.getClassAccess(className,intialize,loader,this);
    }
    
    @IndirectCallers
    protected Class<?> getClassForName(String className) {
        return ModuleSystemAccessor.getClassForName(className,this);
    }
    
    @IndirectCallers
    protected Class<?> getClassForName(String className, boolean initialize, ClassLoader loader) {
        return ModuleSystemAccessor.getClassForName(className,initialize,loader,this);
    }
    
    @IndirectCallers
    protected ClassLoaderAccess getClassLoader(ClassLoader loader) {
        return ModuleSystemAccessor.getClassLoader(loader,this);
    }
    
    @IndirectCallers
    protected ConfigurationAccess getConfiguration(Object configuration) {
        return ModuleSystemAccessor.getConfiguration(configuration,this);
    }
    
    @IndirectCallers
    protected JavaLangAccess getJavaLangAccess() {
        return ModuleSystemAccessor.getJavaLangAccess(this);
    }
    
    @IndirectCallers
    protected JavaLangAccess getJavaLangAccess(Object langAccess) {
        return ModuleSystemAccessor.getJavaLangAccess(langAccess,this);
    }
    
    @IndirectCallers
    protected ModuleAccess getModule(Object module) {
        return ModuleSystemAccessor.getModule(module,this);
    }
    
    @IndirectCallers
    protected ModuleDescriptorAccess getModuleDescriptor(Object moduleDescriptor) {
        return ModuleSystemAccessor.getModuleDescriptor(moduleDescriptor,this);
    }
    
    @IndirectCallers
    protected ModuleDescriptorBuilderAccess getModuleDescriptorBuilder(String moduleName) {
        return ModuleSystemAccessor.getModuleDescriptorBuilder(moduleName,this);
    }
    
    @IndirectCallers
    protected ModuleDescriptorBuilderAccess getModuleDescriptorBuilder(Object builder) {
        return ModuleSystemAccessor.getModuleDescriptorBuilder(builder,this);
    }
    
    @IndirectCallers
    protected ModuleLayerAccess getModuleLayer(Object moduleLayer) {
        return ModuleSystemAccessor.getModuleLayer(moduleLayer,this);
    }
    
    @IndirectCallers
    protected ModuleReferenceAccess getModuleReference(Object moduleReference) {
        return ModuleSystemAccessor.getModuleReference(moduleReference,this);
    }
    
    @IndirectCallers
    protected ResolvedModuleAccess getResolvedModule(Object resolvedModule) {
        return ModuleSystemAccessor.getResolvedModule(resolvedModule,this);
    }
    
    @IndirectCallers
    protected <T> T invokeStatic(String targetName, String methodName, Object ... args) {
        return ModuleSystemAccessor.invokeStatic(this,targetName,methodName,args);
    }
    
    @IndirectCallers
    protected <T> T invokeStatic(Class<?> target, String methodName, Object ... args) {
        return ModuleSystemAccessor.invokeStatic(this,target,methodName,args);
    }
    
    @IndirectCallers
    protected <T> T invokeStatic(String targetName, String methodName, boolean direct,
            Object ... args) {
        return ModuleSystemAccessor.invokeStatic(this,targetName,methodName,direct,args);
    }
    
    @IndirectCallers
    protected <T> T invokeStatic(Class<?> target, String methodName, boolean direct,
            Object ... args) {
        return ModuleSystemAccessor.invokeStatic(this,target,methodName,direct,args);
    }
    
    @IndirectCallers
    protected <T> T invokeStaticDirect(Object accessorOrLogger, Class<?> target, String methodName, Object ... args) {
        return ModuleSystemAccessor.invokeStaticDirect(this,target,methodName,args);
    }
    
    @IndirectCallers
    protected <T> T invokeStaticDirect(String targetClass, String methodName, Object ... args) {
        return ModuleSystemAccessor.invokeStaticDirect(this,targetClass,methodName,args);
    }
    
    @IndirectCallers
    protected ModuleAccess newModule(Object layer, ClassLoader loader, Object descriptor, URI uri) {
        return ModuleSystemAccessor.newModule(this,layer,loader,descriptor,uri);
    }
    
    protected ResolvedModuleAccess newResolvedModule(ConfigurationAccess configuration,
            ModuleReferenceAccess moduleReference) {
        return newResolvedModule(configuration.access,moduleReference.access);
    }
    
    protected ResolvedModuleAccess newResolvedModule(Object configuration, Object moduleReference) {
        return ModuleSystemAccessor.newResolvedModule(this,configuration,moduleReference);
    }
    
    @Override public Logger logger() {
        return this.logger;
    }
}