package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules;

import cpw.mods.cl.ModuleClassLoader;
import cpw.mods.modlauncher.api.IModuleLayerManager.Layer;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ClassAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ConfigurationAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleLayerAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleSystemAccessor;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.NeoForgeCoreLoader;
import org.apache.logging.log4j.Logger;

import java.lang.module.ModuleReference;
import java.lang.module.ResolvedModule;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.BOOT;
import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.GAME;
import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.PLUGIN;
import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.SERVICE;

public class NeoforgeModuleAccess {
    
    /**
     * If the name is not set, this can be used to find it and set it
     */
    public static String calculateLayerName(ModuleLayerAccess layer) {
        if(Objects.nonNull(layer.getLayerName())) return layer.getLayerName();
        Set<Layer> completedLayers = getModuleLayerHandler().completedLayers().keySet();
        return calculateLayerName(layer,completedLayers.toArray(new Layer[0]));
    }
    
    /**
     * If the name is not set, this can be used to find it and set it
     */
    public static String calculateLayerName(ModuleLayerAccess moduleLayer, Layer ... validLayers) {
        if(Objects.nonNull(moduleLayer.getLayerName())) return moduleLayer.getLayerName();
        String name = "UNKNOWN-LAYER";
        for(Layer layer : validLayers) {
            ModuleLayerAccess validLayer = getModuleLayer(layer);
            if(Objects.nonNull(validLayer) && moduleLayer.access()==validLayer.access()) {
                name = layer.name();
                break;
            }
        }
        moduleLayer.setLayerName(name);
        return name;
    }
    
    @IndirectCallers
    public static void cloneModuleTo(ModuleClassLoaderAccess loader, String moduleName, String newName) {
        ModuleLayerAccess layer = loader.getModuleLayer();
        layer.cloneModule(getModuleClassLoader(getLayerFromName(layer.getLayerName())),moduleName,newName);
        loader.configuration().cloneModule(moduleName,newName);
        loader.cloneModule(moduleName,newName);
    }
    
    static Object defaultLogger() {
        return NeoForgeCoreLoader.getLogger();
    }
    
    public static void exportAllPackages(Layer ... layers) {
        exportAllPackages(defaultLogger(),layers);
    }
    
    public static void exportAllPackages(Object accessorOrLogger, Layer ... layers) {
        for(Layer layer : layers) NeoforgeModuleAccess.getModuleLayer(layer,accessorOrLogger).exportPackagesToAll();
    }
    
    public static String findConfigurationLayerName(ConfigurationAccess configuration) {
        Set<Layer> potentialLayers = getModuleLayerHandler().completedLayers().keySet();
        for(Layer potentialLayer : potentialLayers) {
            if(getModuleClassLoader(potentialLayer).configuration().access()==configuration.access())
                return potentialLayer.name();
        }
        return "UNKNOWN LAYER";
    }
    
    @IndirectCallers
    public static ModuleAccess findModuleInAnyLayer(String moduleName) {
        return findModuleInLayers(moduleName,BOOT,SERVICE,PLUGIN,GAME);
    }
    
    public static ModuleAccess findModuleInLayers(String moduleName, Layer ... layers) {
        for(Layer layer : layers) {
            ModuleAccess module = findModuleInLayer(moduleName,layer);
            if(Objects.nonNull(module)) return module;
        }
        Logger logger = ModuleSystemAccessor.getAsLogger(defaultLogger());
        logger.error("Failed to find module {} in the following layers: {}",moduleName,Arrays.toString(layers));
        return null;
    }
    
    public static ModuleAccess findModuleInLayer(String moduleName, Layer layer) {
        return getModuleLayer(layer).getModule(moduleName);
    }
    
    /**
     * We don't want to accidentally override or throw any errors related to getClass
     */
    public static ClassAccess getClassAccess(Class<?> clazz) {
        return Objects.nonNull(clazz) ? ModuleSystemAccessor.getClassAccess(clazz,defaultLogger()) : null;
    }
    
    public static EnvironmentAccess getEnvironment() {
        return getLauncher().environment();
    }
    
    public static EnvironmentAccess getEnvironment(Object accessorOrLogger) {
        return getLauncher(accessorOrLogger).environment();
    }
    
    @IndirectCallers
    public static EnvironmentAccess getEnvironment(Object environment, Object accessorOrLogger) {
        return new EnvironmentAccess(environment,accessorOrLogger);
    }
    
    public static JarMetadataAccess getJarMetadata(Object jarMetadata, Object accessorOrLogger) {
        return new JarMetadataAccess(jarMetadata,accessorOrLogger);
    }
    
    public static LauncherAccess getLauncher() {
        return getLauncher(defaultLogger());
    }
    
    public static LauncherAccess getLauncher(Object accessorOrLogger) {
        return new LauncherAccess(accessorOrLogger);
    }
    
    public static Layer getLayerFromName(String layerName) {
        return Layer.valueOf(layerName);
    }
    
    @IndirectCallers
    public static ClassLoader getLayerClassLoader(Layer layer, Object accessorOrLogger) {
        return getLayerInfo(layer,accessorOrLogger).getClassLoader();
    }
    
    @IndirectCallers
    public static LayerInfoAccess getLayerInfo(Layer layer) {
        return getLayerInfo(layer,defaultLogger());
    }
    
    public static LayerInfoAccess getLayerInfo(Layer layer, Object accessorOrLogger) {
        LayerInfoAccess layerInfo = getModuleLayerHandler(accessorOrLogger).getLayerInfo(layer);
        layerInfo.setLayer(layer);
        return layerInfo;
    }
    
    public static LayerInfoAccess getLayerInfo(Object layerInfo, Object accessorOrLogger) {
        return new LayerInfoAccess(layerInfo,accessorOrLogger);
    }
    
    public static ModFileAccess getModFile(Object modFile, Object accessorOrLogger) {
        return new ModFileAccess(modFile,accessorOrLogger);
    }
    
    public static ModFileInfoAccess getModFileInfo(Object modFileInfo) {
        return getModFileInfo(modFileInfo,defaultLogger());
    }
    
    public static ModFileInfoAccess getModFileInfo(Object modFileInfo, Object accessorOrLogger) {
        return new ModFileInfoAccess(modFileInfo,accessorOrLogger);
    }
    
    public static ModuleAccess getModule(Object module) {
        return ModuleSystemAccessor.getModule(module,defaultLogger());
    }
    
    public static ModuleClassLoaderAccess[] getModuleClassLoaders(Layer... layers) {
        ModuleClassLoaderAccess[] loaders = new ModuleClassLoaderAccess[layers.length];
        for(int i=0;i<layers.length;i++) loaders[i] = getModuleClassLoader(layers[i]);
        return loaders;
    }
    
    public static ModuleClassLoaderAccess getModuleClassLoader(Layer layer) {
        return getLayerInfo(layer,defaultLogger()).getModuleClassLoader();
    }
    
    @IndirectCallers
    public static ModuleClassLoaderAccess getModuleClassLoader(Layer layer, Object accessorOrLogger) {
        return getLayerInfo(layer,accessorOrLogger).getModuleClassLoader();
    }
    
    public static ModuleClassLoaderAccess getModuleClassLoader(ClassLoader loader) {
        return new ModuleClassLoaderAccess((ModuleClassLoader)loader,defaultLogger());
    }
    
    /**
     * Get a ModuleClassLoaderAccess with the input layer name already set
     */
    @IndirectCallers
    public static ModuleClassLoaderAccess getModuleClassLoader(ClassLoader loader, Layer layer) {
        ModuleClassLoaderAccess moduleClassLoader = getModuleClassLoader(loader);
        moduleClassLoader.setLayer(layer);
        return moduleClassLoader;
    }
    
    @IndirectCallers
    public static ModuleClassLoaderAccess getModuleClassLoader(ClassLoader loader, Object accessorOrLogger) {
        return new ModuleClassLoaderAccess((ModuleClassLoader)loader,accessorOrLogger);
    }
    
    public static ModuleLayerAccess getModuleLayer(Object moduleLayer) {
        return ModuleSystemAccessor.getModuleLayer(moduleLayer,defaultLogger());
    }
    
    public static ModuleLayerAccess getModuleLayer(Layer layer) {
        return getModuleLayer(layer,defaultLogger());
    }
    
    public static ModuleLayerAccess getModuleLayer(Layer layer, Object accessorOrLogger) {
        return getModuleLayerHandler(accessorOrLogger).getModuleLayer(layer);
    }
    
    public static ModuleLayerHandlerAccess getModuleLayerHandler() {
        return getEnvironment().getModuleLayerHandler();
    }
    
    public static ModuleLayerHandlerAccess getModuleLayerHandler(Object accessorOrLogger) {
        return getEnvironment(accessorOrLogger).getModuleLayerHandler();
    }
    
    @IndirectCallers
    public static ModuleLayerHandlerAccess getModuleLayerHandler(Object moduleLayerHandler, Object accessorOrLogger) {
        return new ModuleLayerHandlerAccess(moduleLayerHandler,accessorOrLogger);
    }
    
    @IndirectCallers
    public static String getModuleLayerName(ModuleAccess module) {
        return calculateLayerName(module.getLayer());
    }
    
    public static SecureJarAccess getSecureJar(Object secureJar, Object accessorOrLogger) {
        return new SecureJarAccess(secureJar,accessorOrLogger);
    }
    
    public static SecureJarProviderAccess getSecureJarProvider(Object provider) {
        return getSecureJarProvider(provider,defaultLogger());
    }
    
    public static SecureJarProviderAccess getSecureJarProvider(Object provider, Object accessorOrLogger) {
        return new SecureJarProviderAccess(provider,accessorOrLogger);
    }
    
    public static void moveModule(ModuleClassLoaderAccess sourceLoader,
            ModuleClassLoaderAccess targetLoader, String moduleName) {
        moveModule(sourceLoader.getModuleLayer(),targetLoader.layer,moduleName);
    }
    
    public static void moveModule(ModuleLayerAccess layer, Layer targetLayer, String moduleName) {
        ModuleAccess module = layer.removeModuleAndReturn(moduleName);
        if(Objects.isNull(module)) {
            layer.logOrPrintError("Unable to move module "+moduleName+"! Cannot find module in supplier layer "+
                                  layer.getLayerName());
            return;
        }
        moveModuleToLayer(module,targetLayer);
    }
    
    private static void moveModuleClassesTo(ModuleAccess module, ClassLoader target) {
        ClassLoader moduleLoader = module.getClassLoader();
        Logger logger = ModuleSystemAccessor.getAsLogger(defaultLogger());
        if(moduleLoader!=target)
            ModuleSystemAccessor.getClassLoader(moduleLoader,logger).moveModuleClassesTo(module,target);
        else logger.info("Skipping movement of already present module {}",module.getName());
    }
    
    public static void moveModuleToLayer(ModuleAccess module, Layer targetLayer) {
        LayerInfoAccess info = getLayerInfo(targetLayer);
        moveModuleToLayer(module,info.getModuleLayer(),info.getClassLoader(),null);
    }
    
    public static void moveModuleToLayer(ModuleAccess module, ModuleLayerAccess targetLayer,
            ClassLoader targetLoader, String extraModule) {
        ModuleClassLoaderAccess loaderFrom = getModuleClassLoader(module.getClassLoader());
        moveModuleClassesTo(module,targetLoader);
        module.setLayer(targetLayer);
        module.setLoader(targetLoader);
        targetLayer.addModule(module);
        ModuleClassLoaderAccess mTargetLoader = getModuleClassLoader(targetLoader);
        Set<String> packages = module.getPackages();
        String moduleName = module.getName();
        ResolvedModule resolvedModule = (ResolvedModule)loaderFrom.configuration().getModuleDirect(moduleName);
        mTargetLoader.addPackages(packages,resolvedModule);
        ModuleReference root = loaderFrom.getRoot(moduleName).accessAs();
        mTargetLoader.addRoot(moduleName,root);
        if(Objects.nonNull(extraModule)) mTargetLoader.addRoot(extraModule,root);
        mTargetLoader.removeParentLoaders(packages);
        getModuleClassLoader(BOOT).addParentLoaders(packages,targetLoader);
        loaderFrom.removePackages(packages);
        loaderFrom.removeRoot(moduleName);
    }
    
    @IndirectCallers
    public static void printConfigurationGraph(ConfigurationAccess configuration) {
        printConfigurationGraph(configuration,true);
    }
    
    public static void printConfigurationGraph(ConfigurationAccess configuration, boolean printParents) {
        configuration.printGraph(findConfigurationLayerName(configuration));
        if(printParents)
            for(Object parent : configuration.parents())
                printConfigurationGraph(ModuleSystemAccessor.getConfiguration(parent,configuration),true);
    }
    
    @IndirectCallers
    public static void removeResolvedModules(Collection<Layer> layers, String... moduleNames) {
        for(Layer layer : layers) removeResolvedModules(layer,moduleNames);
    }
    
    public static void removeResolvedModules(Layer layer, String... moduleNames) {
        for(String moduleName : moduleNames) removeResolvedModule(layer,moduleName);
    }
    
    public static void removeResolvedModule(Layer layer, String moduleName) {
        getModuleClassLoader(layer).removeModuleFully(moduleName);
    }
    
    public static void setClassModule(ClassAccess c, Layer layer, String moduleName) {
        c.setModule(getModuleLayer(layer),moduleName);
    }
}