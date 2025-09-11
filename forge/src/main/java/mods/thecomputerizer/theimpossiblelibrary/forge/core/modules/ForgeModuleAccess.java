package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ClassAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ConfigurationAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleLayerAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleSystemAccessor;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader.SECURE_CLASSLOADER_FORMAT;

public class ForgeModuleAccess {
    
    public static final String LAYER_ENUM_CLASS = "cpw.mods.modlauncher.api.IModuleLayerManager$Layer";
    
    /**
     * If the name is not set, this can be used to find it and set it
     */
    public static String calculateLayerName(ModuleLayerAccess layer) {
        if(Objects.nonNull(layer.getLayerName())) return layer.getLayerName();
        Set<String> completedLayerNames = new HashSet<>();
        for(Enum<?> completedLayer : getModuleLayerHandler().completedLayers().keySet())
            completedLayerNames.add(completedLayer.toString());
        return calculateLayerName(layer,completedLayerNames.toArray(new String[0]));
    }
    
    /**
     * If the name is not set, this can be used to find it and set it
     */
    public static String calculateLayerName(ModuleLayerAccess layer, String ... validLayerNames) {
        if(Objects.nonNull(layer.getLayerName())) return layer.getLayerName();
        String name = "UNKNOWN-LAYER";
        for(String layerName : validLayerNames) {
            ModuleLayerAccess validLayer = getModuleLayer(layerName);
            if(Objects.nonNull(validLayer) && layer.access()==validLayer.access()) {
                name = layerName;
                break;
            }
        }
        layer.setLayerName(name);
        return name;
    }
    
    public static String changing(String secure, String old) {
        return SECURE_CLASSLOADER_FORMAT ? secure : old;
    }
    
    @IndirectCallers
    public static void cloneModuleTo(ModuleClassLoaderAccess loader, String moduleName, String newName) {
        ModuleLayerAccess layer = loader.getModuleLayer();
        layer.cloneModule(getModuleClassLoader(layer.getLayerName()),moduleName,newName);
        loader.configuration().cloneModule(moduleName,newName);
        loader.cloneModule(moduleName,newName);
    }
    
    static Object defaultLogger() {
        return ForgeCoreLoader.getLogger();
    }
    
    public static void exportAllPackages(String ... layerNames) {
        exportAllPackages(defaultLogger(),layerNames);
    }
    
    public static void exportAllPackages(Object accessorOrLogger, String ... layerNames) {
        for(String layer : layerNames) ForgeModuleAccess.getModuleLayer(layer, accessorOrLogger).exportPackagesToAll();
    }
    
    public static String findConfigurationLayerName(ConfigurationAccess configuration) {
        Set<Enum<?>> potentialLayers = getModuleLayerHandler().completedLayers().keySet();
        for(Enum<?> potentialLayer : potentialLayers) {
            String layerName = potentialLayer.toString();
            if(getModuleClassLoader(layerName).configuration().access()==configuration.access()) return layerName;
        }
        return "UNKNOWN LAYER";
    }
    
    @IndirectCallers
    public static ModuleAccess findModuleInAnyLayer(String moduleName) {
        return findModuleInLayers(moduleName,"BOOT","SERVICE","PLUGIN","GAME");
    }
    
    public static ModuleAccess findModuleInLayers(String moduleName, String ... layerNames) {
        for(String layerName : layerNames) {
            ModuleAccess module = findModuleInLayer(moduleName,layerName);
            if(Objects.nonNull(module)) return module;
        }
        Logger logger = ModuleSystemAccessor.getAsLogger(defaultLogger());
        logger.error("Failed to find module {} in the following layers: {}",moduleName,Arrays.toString(layerNames));
        return null;
    }
    
    public static ModuleAccess findModuleInLayer(String moduleName, String layerName) {
        return getModuleLayer(layerName).getModule(moduleName);
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
    
    @IndirectCallers
    public static ClassLoader getLayerClassLoader(String layerName, Object accessorOrLogger) {
        return getLayerInfo(layerName,accessorOrLogger).getClassLoader();
    }
    
    public static Enum<?> getLayerEnum(String layerName) {
        return ForgeCoreLoader.getEnum(ForgeCoreLoader.bootLoader(), LAYER_ENUM_CLASS, layerName);
    }
    
    @IndirectCallers
    public static LayerInfoAccess getLayerInfo(String layerName) {
        return getLayerInfo(layerName,defaultLogger());
    }
    
    public static LayerInfoAccess getLayerInfo(String layerName, Object accessorOrLogger) {
        LayerInfoAccess layerInfo = getLayerInfo(getLayerEnum(layerName),accessorOrLogger);
        layerInfo.setLayerName(layerName);
        return layerInfo;
    }
    
    public static LayerInfoAccess getLayerInfo(Enum<?> layerEnum, Object accessorOrLogger) {
        return getModuleLayerHandler(accessorOrLogger).getLayerInfo(layerEnum);
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
    
    public static ModuleClassLoaderAccess[] getModuleClassLoaders(String... layerNames) {
        ModuleClassLoaderAccess[] loaders = new ModuleClassLoaderAccess[layerNames.length];
        for(int i=0;i<layerNames.length;i++) loaders[i] = getModuleClassLoader(layerNames[i]);
        return loaders;
    }
    
    public static ModuleClassLoaderAccess getModuleClassLoader(String layerName) {
        return getLayerInfo(layerName,defaultLogger()).getModuleClassLoader();
    }
    
    @IndirectCallers
    public static ModuleClassLoaderAccess getModuleClassLoader(String layerName, Object accessorOrLogger) {
        return getLayerInfo(layerName,accessorOrLogger).getModuleClassLoader();
    }
    
    public static ModuleClassLoaderAccess getModuleClassLoader(ClassLoader loader) {
        return new ModuleClassLoaderAccess(loader,defaultLogger());
    }
    
    /**
     * Get a ModuleClassLoaderAccess with the input layer name already set
     */
    public static ModuleClassLoaderAccess getModuleClassLoader(ClassLoader loader, String layerName) {
        ModuleClassLoaderAccess moduleClassLoader = getModuleClassLoader(loader);
        moduleClassLoader.setLayerName(layerName);
        return moduleClassLoader;
    }
    
    @IndirectCallers
    public static ModuleClassLoaderAccess getModuleClassLoader(ClassLoader loader, Object accessorOrLogger) {
        return new ModuleClassLoaderAccess(loader,accessorOrLogger);
    }
    
    public static ModuleLayerAccess getModuleLayer(Object moduleLayer) {
        return ModuleSystemAccessor.getModuleLayer(moduleLayer,defaultLogger());
    }
    
    public static ModuleLayerAccess getModuleLayer(String layerName) {
        return getModuleLayer(layerName,defaultLogger());
    }
    
    public static ModuleLayerAccess getModuleLayer(String layerName, Object accessorOrLogger) {
        return getModuleLayerHandler(accessorOrLogger).getModuleLayer(layerName);
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
    
    public static void moveModule(String layer, String targetLayer, String moduleName, boolean moveServices) {
        moveModule(getModuleClassLoader(layer),getModuleClassLoader(targetLayer),moduleName,moveServices);
    }
    
    public static void moveModule(ModuleClassLoaderAccess sourceLoader,
            ModuleClassLoaderAccess targetLoader, String moduleName, boolean moveServices) {
        ModuleLayerAccess sourceLayer = sourceLoader.getModuleLayer();
        ModuleAccess module = sourceLayer.removeModuleAndReturn(moduleName);
        if(Objects.isNull(module)) {
            sourceLoader.logOrPrintError("Unable to move module "+moduleName+"! Cannot find module in supplier layer "+
                                         sourceLayer.getLayerName());
            return;
        }
        moveModuleToLayer(sourceLoader,targetLoader,module,null,moveServices);
    }
    
    private static void moveModuleClassesTo(ModuleClassLoaderAccess sourceLoader,
            ModuleClassLoaderAccess targetLoader, ModuleAccess module) {
        if(sourceLoader.access()!=targetLoader.access()) sourceLoader.moveModuleClassesTo(module,targetLoader);
        else sourceLoader.logger().info("Skipping movement of already present module {}",module.getName());
    }
    
    public static void moveModuleToLayer(ModuleClassLoaderAccess sourceLoader, ModuleClassLoaderAccess targetLoader,
            ModuleAccess module, String extraModule, boolean moveServices) {
        ModuleLayerAccess targetLayer = targetLoader.getModuleLayer();
        if(moveServices) sourceLoader.moveServicesTo(targetLayer,module);
        moveModuleClassesTo(sourceLoader,targetLoader,module);
        module.setLayer(targetLayer);
        module.setLoader(targetLoader);
        targetLayer.addModule(module);
        String moduleName = module.getName();
        if(Objects.nonNull(extraModule)) targetLoader.addRoot(extraModule,sourceLoader.getRoot(moduleName).access());
        sourceLoader.moveModuleTo(targetLoader,moduleName,extraModule);
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
    public static void removeResolvedModules(Collection<String> layerNames, String... moduleNames) {
        for(String layerName : layerNames) removeResolvedModules(layerName,moduleNames);
    }
    
    public static void removeResolvedModules(String layerName, String... moduleNames) {
        for(String moduleName : moduleNames) removeResolvedModule(layerName,moduleName);
    }
    
    public static void removeResolvedModule(String layerName, String moduleName) {
        getModuleClassLoader(layerName).removeModuleFully(moduleName);
    }
    
    public static void setClassModule(ClassAccess c, String layerName, String moduleName) {
        c.setModule(getModuleLayer(layerName),moduleName);
    }
}