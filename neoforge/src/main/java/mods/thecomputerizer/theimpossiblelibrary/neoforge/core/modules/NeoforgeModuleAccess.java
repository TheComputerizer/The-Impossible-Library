package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules;

import cpw.mods.cl.ModuleClassLoader;
import cpw.mods.modlauncher.api.IModuleLayerManager.Layer;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ConfigurationAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleLayerAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleSystemAccessor;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ResolvedModuleAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ServicesCatalogAccess;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.NeoForgeCoreLoader;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
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
    
    public static void combineModules(Layer layer, URI combinedLocation, String combinedName, String base,
            String ... others) {
        ModuleClassLoaderAccess loader = getModuleClassLoader(layer);
        if(!base.equals(combinedName)) loader.renameModule(base,combinedName);
        loader.combineModules(combinedLocation,combinedName,others);
    }
    
    static Object defaultLogger() {
        return NeoForgeCoreLoader.getLogger();
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
    public static Layer findMatchingLayer(ClassLoader loader) {
        for(Layer layer : Layer.values())
            if(getModuleClassLoader(layer).unwrap()==loader) return layer;
        return null;
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
     * Find a module in the given layer without logging an error when the module is absent
     */
    @IndirectCallers
    public static ModuleAccess findModuleInLayerNoError(String moduleName, Layer layer) {
        return getModuleLayer(layer).getModule(moduleName,false);
    }
    
    public static ResolvedModuleAccess findResolvedModuleIn(String moduleName, Layer layer) {
        return getModuleClassLoader(layer).configuration().getModule(moduleName);
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
    
    @IndirectCallers
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
    
    public static ModuleAccess getModule(Object module) {
        return ModuleSystemAccessor.getModule(module,defaultLogger());
    }
    
    public static ModuleClassLoaderAccess getModuleClassLoader(@Nullable Layer layer) {
        if(Objects.isNull(layer)) throw new NullPointerException("Tried to get ModuleClassLoader from null Layer!");
        return getLayerInfo(layer,defaultLogger()).getModuleClassLoader();
    }
    
    @IndirectCallers
    public static ModuleClassLoaderAccess getModuleClassLoader(@Nullable Layer layer, Object accessorOrLogger) {
        if(Objects.isNull(layer)) throw new NullPointerException("Tried to get ModuleClassLoader from null Layer!");
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
    
    public static void moveModule(Layer layer, Layer targetLayer, String moduleName, boolean moveClasses) {
        moveModule(getModuleClassLoader(layer),getModuleClassLoader(targetLayer),moduleName,moveClasses);
    }
    
    public static void moveModule(ModuleClassLoaderAccess sourceLoader,
            ModuleClassLoaderAccess targetLoader, ResolvedModuleAccess module, boolean moveClasses) {
        if(Objects.nonNull(module)) moveModule(sourceLoader,targetLoader,module.name(),moveClasses);
        else sourceLoader.logger().error("Cannot move null module from {} to {}",sourceLoader,targetLoader);
    }
    
    public static void moveModule(ModuleClassLoaderAccess sourceLoader,
            ModuleClassLoaderAccess targetLoader, String moduleName, boolean moveClasses) {
        ModuleLayerAccess sourceLayer = sourceLoader.getModuleLayer();
        ModuleAccess module = sourceLayer.removeModuleAndReturn(moduleName);
        if(Objects.isNull(module)) {
            sourceLoader.logOrPrintError("Unable to move module "+moduleName+"! Cannot find module in source "+
                                         "layer "+sourceLayer.getLayerName());
            return;
        }
        moveModuleToLayer(sourceLoader,targetLoader,module,moveClasses);
    }
    
    private static void moveModuleClassesTo(ModuleClassLoaderAccess sourceLoader,
            ModuleClassLoaderAccess targetLoader, ModuleAccess module) {
        if(sourceLoader.access()!=targetLoader.access()) sourceLoader.moveModuleClassesTo(module,targetLoader);
        else sourceLoader.logger().info("Skipping movement of already present module {}",module.getName());
    }
    
    public static void moveModuleToLayer(ModuleClassLoaderAccess sourceLoader,
            ModuleClassLoaderAccess targetLoader, ModuleAccess module, boolean moveClasses) {
        ModuleLayerAccess targetLayer = targetLoader.getModuleLayer();
        ServicesCatalogAccess targetServices = targetLayer.getServicesCatalog();
        if(moveClasses) {
            sourceLoader.logger().info("Moving classes");
            moveModuleClassesTo(sourceLoader,targetLoader,module);
        }
        sourceLoader.moveModuleTo(targetLoader,module.getName());
        targetServices.registerModule(module);
        module.setLayer(targetLayer);
        module.setLoader(targetLoader);
        targetLayer.addModule(module);
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
    
    public static void renameModule(Layer layer, String name, String newName) {
        ModuleClassLoaderAccess loader = getModuleClassLoader(layer);
        Logger logger = loader.logger();
        if(Objects.isNull(name) || Objects.isNull(newName)) {
            logger.error("Tried to rename {} module {} to {}",layer,name,newName);
            return;
        }
        if(name.equals(newName)) {
            logger.info("{} module name is already equal to {}",layer,newName);
            return;
        }
        loader.renameModule(name,newName);
    }
}