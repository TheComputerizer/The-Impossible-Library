package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import org.apache.logging.log4j.Logger;

import java.net.URI;

public abstract class AbstractModuleSystemAccessor implements ModuleSystemAccessor {
    
    protected static String changing(String secure, String old) {
        return ModuleSystemAccessor.changing(secure,old);
    }
    
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
    protected EnvironmentAccess getEnvironment() {
        return ModuleSystemAccessor.getEnvironment(this);
    }
    
    @IndirectCallers
    protected EnvironmentAccess getEnvironment(Object environment) {
        return ModuleSystemAccessor.getEnvironment(environment,this);
    }
    
    @IndirectCallers
    protected JarMetadataAccess getJarMetadata(Object jarMetadata) {
        return ModuleSystemAccessor.getJarMetadata(jarMetadata,this);
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
    protected LauncherAccess getLauncher() {
        return ModuleSystemAccessor.getLauncher(this);
    }
    
    @IndirectCallers
    protected ClassLoader getLayerClassLoader(String layerName) {
        return ModuleSystemAccessor.getLayerClassLoader(layerName,this);
    }
    
    @IndirectCallers
    protected LayerInfoAccess getLayerInfo(String layerName) {
        return ModuleSystemAccessor.getLayerInfo(layerName,this);
    }
    
    @IndirectCallers
    protected LayerInfoAccess getLayerInfo(Enum<?> layerEnum) {
        return ModuleSystemAccessor.getLayerInfo(layerEnum,this);
    }
    
    @IndirectCallers
    protected LayerInfoAccess getLayerInfo(Object layerInfo) {
        return ModuleSystemAccessor.getLayerInfo(layerInfo,this);
    }
    
    @IndirectCallers
    protected ModuleClassLoaderAccess getLayerModuleClassLoader(String layerName) {
        return ModuleSystemAccessor.getLayerModuleClassLoader(layerName,this);
    }
    
    @IndirectCallers
    protected ModFileAccess getModFile(Object modFile) {
        return ModuleSystemAccessor.getModFile(modFile,this);
    }
    
    @IndirectCallers
    protected ModFileInfoAccess getModFileInfo(Object modFileInfo) {
        return ModuleSystemAccessor.getModFileInfo(modFileInfo,this);
    }
    
    @IndirectCallers
    protected ModuleAccess getModule(Object module) {
        return ModuleSystemAccessor.getModule(module,this);
    }
    
    @IndirectCallers
    protected ModuleClassLoaderAccess getModuleClassLoader(String layerName) {
        return ModuleSystemAccessor.getModuleClassLoader(layerName,this);
    }
    
    @IndirectCallers
    protected ModuleClassLoaderAccess getModuleClassLoader(ClassLoader loader) {
        return ModuleSystemAccessor.getModuleClassLoader(loader,this);
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
    protected ModuleFinderAccess getModuleFinder(Object moduleFinder) {
        return ModuleSystemAccessor.getModuleFinder(moduleFinder,this);
    }
    
    @IndirectCallers
    protected ModuleLayerAccess getModuleLayer(Object moduleLayer) {
        return ModuleSystemAccessor.getModuleLayer(moduleLayer,this);
    }
    
    @IndirectCallers
    protected ModuleLayerAccess getModuleLayer(String layerName) {
        return ModuleSystemAccessor.getModuleLayer(layerName,this);
    }
    
    protected ModuleLayerHandlerAccess getModuleLayerHandler() {
        return ModuleSystemAccessor.getModuleLayerHandler(this);
    }
    
    @IndirectCallers
    protected ModuleLayerHandlerAccess getModuleLayerHandler(Object moduleLayerHandler) {
        return ModuleSystemAccessor.getModuleLayerHandler(moduleLayerHandler,this);
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
    protected SecureJarAccess getSecureJar(Object secureJar) {
        return ModuleSystemAccessor.getSecureJar(secureJar,this);
    }
    
    @IndirectCallers
    protected SecureJarProviderAccess getSecureJarProvider(Object provider) {
        return ModuleSystemAccessor.getSecureJarProvider(provider,this);
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
    
    protected ModuleAccess newModule(ModuleLayerAccess moduleLayer, ClassLoaderAccess loaderAccess,
            ModuleDescriptorAccess moduleDescriptor, URI uri) {
        return newModule(moduleLayer,loaderAccess.unwrap(),moduleDescriptor,uri);
    }
    
    protected ModuleAccess newModule(ModuleLayerAccess moduleLayer, ClassLoader loader,
            ModuleDescriptorAccess moduleDescriptor, URI uri) {
        return newModule(moduleLayer.access,loader,moduleDescriptor.access,uri);
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