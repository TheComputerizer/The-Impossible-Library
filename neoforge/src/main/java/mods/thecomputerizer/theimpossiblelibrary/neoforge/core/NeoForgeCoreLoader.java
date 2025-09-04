package mods.thecomputerizer.theimpossiblelibrary.neoforge.core;

import cpw.mods.modlauncher.api.IModuleLayerManager.Layer;
import io.github.toolfactory.jvm.function.catalog.ConsulterSupplyFunction;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ClassAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ConfigurationAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleLayerAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleReferenceAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ResolvedModuleAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ServicesCatalogAccess;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules.LayerInfoAccess;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules.ModFileInfoAccess;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules.ModuleClassLoaderAccess;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules.ModuleReferenceHolder;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules.NeoforgeModuleAccess;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules.ResolvedModuleHolder;
import net.neoforged.neoforgespi.language.IModFileInfo;
import net.neoforged.neoforgespi.language.IModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.lang.module.ResolvedModule;
import java.util.*;
import java.util.function.Consumer;

import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.BOOT;
import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.GAME;
import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.PLUGIN;
import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.SERVICE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;

/**
 * Figures out which version to load on and how to load stuff on it
 */
@SuppressWarnings({"unused","LoggingSimilarMessage"})
public class NeoForgeCoreLoader { //TODO So much of this is obselete now :(
    
    public static final boolean MODULE_LAYERS = Boolean.parseBoolean(System.getProperty("til.debug.neoforge.modules.layers","true"));
    private static final String API_PKG = "mods.thecomputerizer.theimpossiblelibrary.api";
    private static final String CORE_PKG = API_PKG+".core";
    private static final String COREAPI_CLASS = CORE_PKG+".CoreAPI";
    private static final String NEOFORGE_PKG = "mods.thecomputerizer.theimpossiblelibrary.neoforge";
    private static final String NEOFORGE_CORE_PKG = NEOFORGE_PKG+".core";
    private static final Logger LOGGER = LogManager.getLogger("TIL NeoforgeCoreLoader");
    
    //The module system forced me to find a very powerful alternative, but at least I don't need to do Unsafe hacking
    static {
        Hacks.checkBurningWaveInit();
        if(NeoForgeCoreLoader.class.getClassLoader()!=bootLoader())
            LOGGER.info("I see you are running Java 9+ so I'll be using burningwave to break its strong encapsulation");
    }
    
    /**
     * Adds given module and related info to all the relevant objects.
     */
    static void addModuleThouroughly(
            ModuleAccess module, ResolvedModuleAccess resolvedModule, String name,
            Set<String> packages, ModuleReferenceAccess moduleRef, ModuleClassLoaderAccess target) {
        addModuleThouroughly(module,resolvedModule,target.getModuleLayer(),name,packages,moduleRef,target);
    }
    
    /**
     * Adds given module and related info to all the relevant objects.
     */
    static void addModuleThouroughly(
            ModuleReferenceHolder ref, String name, ModuleClassLoaderAccess target,
            Set<String> finalizedPkgs) {
        addModuleThouroughly(ref.module(), target.getModuleLayer(), name, ref.reference(), target, finalizedPkgs);
    }
    
    /**
     * Adds given module and related info to all the relevant objects.
     */
    static void addModuleThouroughly(ModuleAccess module, ModuleLayerAccess moduleLayer, String name,
            ModuleReferenceAccess moduleRef, ModuleClassLoaderAccess target, Set<String> finalizedPkgs) {
        ResolvedModuleAccess resolvedModule = target.configuration().newResolvedModule(moduleRef);
        addModuleThouroughly(module,resolvedModule,moduleLayer,name,moduleRef,target,finalizedPkgs);
    }
    
    /**
     * Adds given module and related info to all the relevant objects.
     */
    static void addModuleThouroughly(ModuleAccess module, ResolvedModuleAccess resolvedModule,
            ModuleLayerAccess moduleLayer, String name, ModuleReferenceAccess moduleRef,
            ModuleClassLoaderAccess target, Set<String> finalizedPkgs) {
        Set<String> packages = resolvedModule.filteredPackages(finalizedPkgs);
        addModuleThouroughly(module,resolvedModule,moduleLayer,name,packages,moduleRef,target);
    }
    
    /**
     * Adds given module and related info to all the relevant objects.
     */
    static void addModuleThouroughly(ModuleAccess module, ResolvedModuleAccess resolvedModule,
            ModuleLayerAccess moduleLayer, String name, Set<String> packages,
            ModuleReferenceAccess moduleRef, ModuleClassLoaderAccess target) {
        module.setName(name);
        target.addRoot(name,moduleRef);
        target.addPackages(packages,resolvedModule);
        target.parentLoaders().entrySet().removeIf(entry -> packages.contains(entry.getKey()));
        ConfigurationAccess configuration = target.configuration();
        configuration.removeModule(name);
        configuration.addModuleIfAbsent(name,resolvedModule);
        if(!moduleLayer.hasMatchingModuleInSet(module.getName())) moduleLayer.addModule(module);
        module.setLayer(moduleLayer);
        module.setLoader(target);
    }
    
    /**
     * So basically the only way to guaruntee stuff will work in the BOOT layer is if it can find the right
     * package in the right ResolvedModule. Luckily we already have those in the current (SERVICE) layer, so
     * all we need to do is transfer some stuff over and then handle the duplicates
     */
    private static void addResolvedModule(ResolvedModuleHolder source,
            ModuleClassLoaderAccess targetLoader, Consumer<ResolvedModuleHolder> ifValidMovement) {
        ModuleClassLoaderAccess sourceLoader = source.loader();
        ResolvedModuleAccess sourceModule = source.module();
        if(sourceLoader.access()==targetLoader.access()) {
            LOGGER.debug("Not adding module {} to matching source loader {}",sourceModule.name(),
                         targetLoader.layerName());
            return;
        }
        
        String moduleName = sourceModule.name();
        Set<String> pkgs = sourceModule.packages();
        targetLoader.addRoot(sourceModule);
        targetLoader.addPackages(sourceModule);
        
        //Finalize by moving the original Module from the source to the targer layer & fixing parent loaders
        NeoforgeModuleAccess.moveModule(sourceLoader,targetLoader,moduleName);
        sourceLoader.addParentLoaders(pkgs,targetLoader);
        ifValidMovement.accept(source);
        
        //Fix configurations & prevent reading duplicate modules
        targetLoader.configuration().addModuleIfAbsent(sourceModule);
        sourceLoader.configuration().removeModule(sourceModule);
        sourceLoader.removeRoot(moduleName);
        
        LOGGER.debug("Finished migrating module {} from the {} layer to the {} layer",moduleName,
                     sourceLoader.layerName(),targetLoader.layerName());
    }
    
    /**
     * Should be the ClassLoader for the BOOT layer or the system ClassLoader if Java 8
     */
    public static ClassLoader bootLoader() {
        return bootLoaderAccess().unwrap();
    }
    
    static ModuleClassLoaderAccess bootLoaderAccess() {
        return NeoforgeModuleAccess.getModuleClassLoader(BOOT);
    }
    
    /**
     * Export the given module to all packages loaded to a module in the GAME layer
     */
    public static void exportAllModules() {
        NeoforgeModuleAccess.exportAllPackages(BOOT,SERVICE,PLUGIN,GAME);
    }
    
    /**
     * Returns an array where the elements are the ClassLoader, resolved module, and the name of the layer.
     * Assumes the given loaders array will always be in the order of BOOT, SERVICE, PLUGIN, GAME
     */
    public static Object[] findModuleLoaderForPackage(String pkg, ModuleClassLoaderAccess ... loaders) {
        for(int i=0;i<loaders.length;i++) {
            ModuleClassLoaderAccess loader = loaders[i];
            String name = i==0 ? "BOOT" : (i==1 ? "SERVICE" : "PLUGIN");
            ResolvedModuleAccess resolvedModule = loader.getResolvedModule(pkg);
            if(Objects.nonNull(resolvedModule)) return new Object[]{loader,resolvedModule,name};
        }
        return null;
    }
    
    public static void fixForServiceLayer() {
        if(!MODULE_LAYERS) {
            handleDevLoading(1);
            return;
        }
        LOGGER.info("Running SERVICE layer fix");
        String pkg = NeoForgeCoreLoader.class.getPackage().getName();
        ResolvedModuleHolder holder = ResolvedModuleHolder.findPackage(pkg,BOOT,SERVICE);
        if(Objects.nonNull(holder)) {
            LOGGER.debug("Found module {} for {} in layer {}",holder.moduleName(),pkg,holder.layerName());
            ModuleClassLoaderAccess targetLoader = bootLoaderAccess();
            Consumer<ResolvedModuleHolder> ifValidMovement = ResolvedModuleHolder::removePackagesFromLoader;
            addResolvedModule(holder,targetLoader,ifValidMovement);
        } else LOGGER.fatal("FAILED TO GET RESOLVED MODULE FOR {}",pkg);
    }
    
    public static void fixService(String service, String impl, ClassLoader loaderFrom) {
        fixService(service,impl,loaderFrom,false);
    }
    
    public static void fixService(String service, String impl, ClassLoader loaderFrom, boolean isRemoval) {
        Class<?> coreClass = NeoForgeCoreLoader.class;
        ModuleClassLoaderAccess bootLoader = bootLoaderAccess();
        if(bootLoader.unwrap()!=coreClass.getClassLoader()) {
            Hacks.invokeStatic(Hacks.findClass(coreClass.getName(),bootLoader.unwrap()),"fixService",service,
                               impl,loaderFrom,isRemoval);
            return;
        }
        LOGGER.info("Attempting to fix service {} (implementation of {})",impl,service);
        String pkg = ConsulterSupplyFunction.class.getPackage().getName();
        ResolvedModule resolvedModule = bootLoader.packageLookup().get(pkg);
        if(Objects.isNull(resolvedModule)) {
            LOGGER.error("Failed to get module from package! {}",pkg);
            return;
        }
        String name = resolvedModule.name();
        ModuleLayerAccess serviceLayer = NeoforgeModuleAccess.getModuleLayer(SERVICE);
        ModuleLayerAccess bootLayer = bootLoader.getModuleLayer();
        Map<String,Object> nameToModule = bootLayer.nameToModule();
        Module module = (Module)bootLayer.nameToModule().get(name);
        if(Objects.isNull(module)) {
            LOGGER.error("Failed to get module {} in BOOT layer!",name);
            return;
        }
        fixServiceFor(service,impl,module,bootLayer,isRemoval);
        if(!isRemoval) {
            fixServiceFor(service,impl,module,serviceLayer,true);
            try {
                Class<?> implClass = Class.forName(impl,false,bootLoader.unwrap());
                Hacks.setFieldDirect(implClass,"module",module);
                Hacks.setFieldDirect(implClass,"classLoader",bootLoader.unwrap());
            } catch(ClassNotFoundException ignored) {} //The class won't be found when loading in 1.20.4
        }
        LOGGER.info("Sucessfully notified the ServicesCatalog that {} has been moved",impl);
    }
    
    private static void fixServiceFor(String service, String impl, Module module, Object layer,
            boolean isRemoval) {
        ServicesCatalogAccess catalog = Hacks.invoke(layer,"getServicesCatalog");
        if(Objects.nonNull(catalog)) {
            if(isRemoval) {
                if(catalog.removeImplementations(service,impl))
                    LOGGER.debug("Successfully removed service implementation {} (service={})",impl,service);
                else LOGGER.debug("Service implementation was not present {} (service={})",impl,service);
            }
            else {
                catalog.addProvider(service,module,impl);
                LOGGER.debug("Successfully added service implementation {} (service={})",impl,service);
            }
        }
    }
    
    public static @Nullable CoreAPI getBootLoadedCoreAPI() {
        return getCoreAPIReflectively(bootLoaderAccess());
    }
    
    static CoreAPI getCoreAPIReflectively(ModuleClassLoaderAccess loader) {
        CoreAPI coreInstance;
        try {
            Class<?> c = ClassHelper.existsOn(COREAPI_CLASS,loader.unwrap());
            coreInstance = Objects.nonNull(c) ? Hacks.getFieldStatic(c,"INSTANCE") : null;
        } catch(Throwable ignored) {
            coreInstance = null;
        }
        if(Objects.isNull(coreInstance)) LOGGER.debug("CoreAPI not found on {} layer",loader.layerName());
        return coreInstance;
    }
    
    public static Object getLogger() {
        return LOGGER;
    }
    
    /**
     * Returns the index of the first matching element in the array or -1 if nothing matches
     */
    @SuppressWarnings("SameParameterValue")
    static <T> int getMatchingArrayIndex(T[] array, T value) {
        if(Objects.isNull(array) || array.length==0) return -1;
        for(int i=0;i<array.length;i++)
            if(value.equals(array[i])) return i;
        return -1;
    }
    
    static String getVersionStr() {
        Class<?> accessClass = Hacks.findClass(NeoforgeModuleAccess.class.getName(),bootLoader());
        Object launcher = Hacks.invokeStatic(accessClass,"getLauncher");
        Object handler = Objects.nonNull(launcher) ? Hacks.invoke(launcher,"argumentHandler") : null;
        if(Objects.isNull(handler)) return null;
        String[] rawArgs = Hacks.invoke(handler,"getArgs");
        if(Objects.isNull(rawArgs)) {
            LOGGER.error("Failed to find version using handler {}",handler);
            return null;
        }
        int versionIndex = getMatchingArrayIndex(rawArgs,"--fml.mcVersion")+1;
        if(versionIndex>0) {
            LOGGER.debug("Found fml.mcVersion arg at index {} -> {}",versionIndex,rawArgs[versionIndex]);
            return rawArgs[versionIndex];
        }
        LOGGER.error("Failed to find fml.mcVersion or version flags from args {}",Arrays.toString(rawArgs));
        return null;
    }
    
    static void handleDevLoading(int stage) {
        final String appendArg = "(-Dtil.debug.neoforge.modules.layers=false)";
        Set<Layer> completedLayers = NeoforgeModuleAccess.getModuleLayerHandler().completedLayers().keySet();
        switch(stage) {
            case 1: {
                LOGGER.debug("Skipping module layer movement hacks {}",appendArg);
                boolean bootLoaded = NeoforgeModuleAccess.class.getClassLoader()==bootLoader();
                LOGGER.debug("NeoforgeCoreLoader is boot loaded: {}",bootLoaded);
                //logModuleNames("BOOT","SERVICE");
                LOGGER.debug("Completed layers: {}",completedLayers);
                return;
            }
            case 2: {
                LOGGER.debug("Completed layers: {}",completedLayers);
                return;
            }
            case 3: {
                LOGGER.debug("Skipping PLUGIN layer module resyncing {}",appendArg);
                //logModuleNames("BOOT","SERVICE","PLUGIN");
                LOGGER.debug("Completed layers: {}",completedLayers);
                return;
            }
            case 4: {
                LOGGER.debug("Finalizing dev packages {}",appendArg);
                ModuleClassLoaderAccess bootLoader = bootLoaderAccess();
                ModuleLayerAccess bootLayer = bootLoader.getModuleLayer();
                LayerInfoAccess gameLayerInfo = NeoforgeModuleAccess.getLayerInfo(GAME);
                ModuleClassLoaderAccess gameLoader = gameLayerInfo.getModuleClassLoader();
                ModuleLayerAccess gameLayer = gameLayerInfo.getModuleLayer();
                ModuleAccess module = gameLayer.getModule(MODID);
                if(Objects.nonNull(module)) {
                    Set<String> allPackages = new HashSet<>();
                    for(String moduleName : new String[]{"main","tilneoforge"}) {
                        LOGGER.debug("Moving module {} classes to game layer",moduleName);
                        ModuleAccess bootModule = bootLayer.getModule(moduleName);
                        if(Objects.nonNull(bootModule)) {
                            Set<String> packages = bootModule.getPackages();
                            gameLoader.inheritClasses(bootModule,moduleName,bootLoader);
                            allPackages.addAll(packages);
                            gameLoader.addPackages(packages,bootLoader.configuration().getModule(moduleName));
                            gameLoader.addRoot(moduleName,bootLoader.getRootDirect(moduleName));
                        }
                        else LOGGER.debug("Module {} not found in the boot layer",moduleName);
                    }
                    module.addPackages(allPackages);
                } else LOGGER.error("Failed to find dev module {}! Cannot finalize packages!",MODID);
                LOGGER.debug("Finalized dev packages {}",appendArg);
                return;
            }
            default: {
                LOGGER.error("Unknown dev loading stage {} {}",stage,appendArg);
            }
        }
    }
    
    /**
     * Returns a CoreAPI instance on the input ClassLoader. Initializes the source if necessary
     */
    public static @Nullable CoreAPI initCoreAPI(ClassLoader loader) {
        LOGGER.debug("Starting CoreAPI init");
        CoreAPI bootInstance = getBootLoadedCoreAPI();
        if(Objects.nonNull(bootInstance)) {
            LOGGER.debug("Returning existing CoreAPI instance found in the BOOT layer");
            return bootInstance;
        }
        String version = getVersionStr();
        Class<?> coreClass = loadAPI(version,bootLoader()); //Throws an exception instead of returning null
        try {
            return Hacks.construct(coreClass);
        } catch(Throwable t) {
            LOGGER.fatal("Unknown error while trying to get CoreAPI instance as {}",coreClass,t);
        }
        LOGGER.fatal("Failed to initialize CoreAPI [Neoforge-{}] using {}",version,loader);
        return null;
    }
    
    public static boolean isJava21() {
        return System.getProperty("java.version").startsWith("21");
    }
    
    /**
     * Tries to get the ClassLoader instance associated with the given layer name
     */
    public static ClassLoader layerClassLoader(Layer layer) {
        return NeoforgeModuleAccess.getModuleClassLoader(layer).unwrap();
    }
    
    /**
     * Define necessary classes for the versioned CoreAPI instance
     * Returns the instance class
     */
    static Class<?> loadAPI(String version, ClassLoader loader) {
        Class<?> clazz = Hacks.findClass(versionClassName("core.TILCoreNeoForge",version),loader,true);
        if(Objects.isNull(clazz)) throw new RuntimeException("Failed to load CoreAPI instance [Neoforge-"+version+"]");
        LOGGER.debug("Successfully loaded CoreAPI instance {}",clazz);
        return clazz;
    }
    
    @SuppressWarnings("SameParameterValue")
    static void loadNewModuleTo(@Nullable IModInfo mod, String targetLayerName, Set<String> finalizedPkgs) {
        if(Objects.isNull(mod)) {
            LOGGER.error("Cannot load module from nonexistent file!");
            return;
        }
        try {
            String modid = mod.getModId(); //Usually the same as the jar name, but there are some edge cases...
            ModFileInfoAccess fileInfo = NeoforgeModuleAccess.getModFileInfo(mod.getOwningFile());
            ModuleClassLoaderAccess[] loaders = NeoforgeModuleAccess.getModuleClassLoaders(BOOT,SERVICE,PLUGIN);
            ModuleClassLoaderAccess targetLoader = NeoforgeModuleAccess.getModuleClassLoader(GAME);
            Consumer<String> jarNameMismatchHandler = jarName -> nukeLoaderFields(jarName,loaders,targetLoader);
            ModuleReferenceHolder referenceHolder = fileInfo.getJarModule(targetLoader,modid,jarNameMismatchHandler);
            addModuleThouroughly(referenceHolder,modid,targetLoader,finalizedPkgs);
            LOGGER.info("Finished setting up {}",referenceHolder.module());
            
            //nuke & finalize
            nukeLoaderFields(modid,loaders);
            targetLoader.inheritClasses(referenceHolder.module(),new String[]{fileInfo.jarName(),modid},loaders);
            LOGGER.warn("------------------------------------------------------------------------------------------------");
            LOGGER.warn("SUCCESSFULLY LOADED {} TO THE GAME LAYER HAVE A NICE DAY", modid);
            LOGGER.warn("------------------------------------------------------------------------------------------------");
        } catch(Throwable t) {
            LOGGER.error("Failed to load new module!",t);
        }
    }
    
    public static void logModuleNames(Layer ... layers) {
        LOGGER.debug("Printing all module names for the following layers: {}",(Object)layers);
        NeoforgeModuleAccess.getModuleLayerHandler().printAllModuleNames(layers);
        LOGGER.debug("Finished printing all requested module names");
    }
    
    public static void logLayerPaths(Layer ... layers) {
        LOGGER.debug("Printing all paths for the following layers: {}",(Object)layers);
        NeoforgeModuleAccess.getModuleLayerHandler().printLayerPaths(layers);
        LOGGER.debug("Finished printing all requested paths");
    }
    
    /**
     * Add the module for the given package to the GAME layer and nuke all references to it from other layers
     */
    public static void nukeAndFinalize(IModInfo mod, String pkg, Set<String> finalizedPkgs) {
        ModuleClassLoaderAccess[] loaders = NeoforgeModuleAccess.getModuleClassLoaders(BOOT,SERVICE,PLUGIN);
        if(MODULE_LAYERS) {
            LOGGER.info("Finalizing package {}",pkg);
            ResolvedModuleHolder holder = ResolvedModuleHolder.findPackage(pkg,loaders);
            nukeAndFinalize(mod,holder,finalizedPkgs,true,loaders);
        } else handleDevLoading(4);
    }
    
    /**
     * Add the module to the GAME layer and nuke all references to it from other layers
     */
    public static void nukeAndFinalizeModule(IModInfo mod, String moduleName, Set<String> finalizedPkgs,
            ModuleClassLoaderAccess ... loaders) {
        LOGGER.info("Finalizing module {}",moduleName);
        ResolvedModuleHolder holder = ResolvedModuleHolder.findModule(moduleName,loaders);
        nukeAndFinalize(mod,holder,finalizedPkgs,false,loaders);
    }
    
    private static void nukeAndFinalize(IModInfo mod, ResolvedModuleHolder holder, Set<String> finalizedPkgs,
            boolean bigLog, ModuleClassLoaderAccess ... loaders) {
        if(Objects.isNull(holder)) {
            loadNewModuleTo(mod,"GAME",finalizedPkgs);
            return;
        }
        ResolvedModuleAccess resolvedModule = holder.module();
        String name = resolvedModule.name();
        LOGGER.debug("Got resolved module as {}({})",resolvedModule,name);
        if(bigLog) {
            LOGGER.warn("------------------------------------------------------------------------------------------------");
            LOGGER.warn("NUKING ALL REFERENCES OF MODULE {} FROM THE BOOT, SERVICE, & PLUGIN LAYERS",name);
            LOGGER.warn("------------------------------------------------------------------------------------------------");
        } else LOGGER.debug("Nuking BOOT, SERVICE, & PLUGIN layer references to module {}",name);
        ModuleClassLoaderAccess foundLoader = holder.loader();
        ModuleAccess module = foundLoader.getModuleLayer().getModule(name);
        ModuleClassLoaderAccess target = NeoforgeModuleAccess.getModuleClassLoader(GAME);
        Set<String> packages = resolvedModule.packages(true);
        packages.removeAll(finalizedPkgs);
        finalizedPkgs.addAll(packages);
        addModuleThouroughly(module,resolvedModule,name,packages,foundLoader.getRoot(name),target);
        
        //nuke & finalize
        nukeLoaderFields(name,loaders);
        target.inheritClasses(module,name,loaders);
        if(bigLog) {
            LOGGER.warn("------------------------------------------------------------------------------------------------");
            LOGGER.warn("MODULE {} HAS BEEN SUCCESSFULLY MOVED TO THE GAME LAYER HAVE A NICE DAY",name);
            LOGGER.warn("------------------------------------------------------------------------------------------------");
        } else LOGGER.debug("Finalized module {}",name);
    }
    
    static void nukeLoaderFields(String moduleName, ModuleClassLoaderAccess[] loaders,
            ModuleClassLoaderAccess ... otherLoaders) {
        nukeLoaderFields(moduleName,loaders);
        nukeLoaderFields(moduleName,otherLoaders);
    }
    
    /**
     * Remove references to the input module from the associated ModuleClassLoader instances.
     */
    static void nukeLoaderFields(String moduleName, ModuleClassLoaderAccess ... loaders) {
        for(ModuleClassLoaderAccess loader : loaders) loader.removeModuleFully(moduleName);
    }
    
    public static void removeDevModules(String ... layers) {
        if(!MODULE_LAYERS) handleDevLoading(2);
    }
    
    public static void removeServiceFrom(String service, String impl, Layer layer) {
        Hacks.checkBurningWaveInit();
        LOGGER.info("Attempting to fix service {} (implementation of {})",impl,service);
        NeoforgeModuleAccess.getModuleLayer(layer).removeServiceImplementations(service,impl);
        LOGGER.info("Sucessfully removed all service providers from {} layer for {}",layer,impl);
    }
    
    /**
     * Since this class is intially loaded in the SERVICE layer which has BOOT as a parent separate from PLUGIN,
     * we need a workaround for the PLUGIN layer thinking there are duplicate modules.
     * This is needed since IModLanguageProvider implementations are forced into PLUGIN layer from service loading and
     * can likely only be called via reflection.
     */
    public static void resyncModules(ClassLoader loaderTo, Layer layerTo, ClassLoader loaderFrom) {
        if(!MODULE_LAYERS) { //Resolve differently in dev since the modules are initially loaded in the BOOT layer
            handleDevLoading(3);
            return;
        }
        resyncModules(NeoforgeModuleAccess.getModuleClassLoader(loaderTo),layerTo,
                      NeoforgeModuleAccess.getModuleClassLoader(loaderFrom));
    }
    
    private static void resyncModules(ModuleClassLoaderAccess loaderTo, Layer layerTo,
            ModuleClassLoaderAccess loaderFrom) {
        LOGGER.info("Resyncing module to {}",layerTo);
        if(layerTo!=PLUGIN) loaderFrom.configuration().addModule(loaderFrom.getResolvedModule(NEOFORGE_CORE_PKG));
        
        //Remove module from PLUGIN layer
        ResolvedModuleAccess resolvedModule = loaderTo.getResolvedModule(NEOFORGE_CORE_PKG);
        String name = resolvedModule.name();
        ConfigurationAccess configuration = loaderTo.configuration();
        configuration.removeModule(resolvedModule);
        Set<String> packages = resolvedModule.packages();
        
        //Finalize by dealing with the module layers & fixing parent loaders
        NeoforgeModuleAccess.getModuleLayer(layerTo).removeModule(name);
        loaderTo.addParentLoaders(resolvedModule.packages(),loaderFrom);
        loaderTo.removeRoot(name);
        
        loaderTo.removePackagesForModule(resolvedModule); //Prevent reading duplicate modules
    }
    
    public static void sanityCheckModule(Class<?> c, String name) {
        ClassAccess access = NeoforgeModuleAccess.getClassAccess(c);
        if(Objects.isNull(access)) {
            LOGGER.error("Failed to get ClassAccess for {}! Cannot run sanity check",c);
            return;
        }
        String moduleName = access.getModuleName();
        if(!name.equals(moduleName)) {
            //By this point the class is definitely in the GAME layer regardless of whether the module is correct
            NeoforgeModuleAccess.setClassModule(access,GAME,name);
            LOGGER.info("Moved {} from module {} to module {}",c,moduleName,name);
        }
    }
    
    public static void verifyModule(String className, IModInfo info, Object moduleLayer) {
        LOGGER.info("Verifying that {} is valid for {} and can be found in {}",className,info,moduleLayer);
        String modid = info.getModId();
        IModFileInfo fileInfo = info.getOwningFile();
        String moduleName = fileInfo.moduleName();
        if(!modid.equals(moduleName)) LOGGER.error("Mod id {} does not equal module name {}!",modid,moduleName);
        ModuleLayerAccess layerAccess = NeoforgeModuleAccess.getModuleLayer(moduleLayer);
        Optional<Object> optionalModule = layerAccess.findModule(moduleName);
        if(optionalModule.isEmpty()) {
            layerAccess.findAndAddModule(moduleLayer,moduleName,modid);
            optionalModule = layerAccess.findModule(moduleName);
        }
        if(optionalModule.isPresent())
            NeoforgeModuleAccess.getModule(optionalModule.get()).addClassIfMissing(className,layerAccess);
        else LOGGER.error("Module {} is not present in the target layer!",moduleName);
        LOGGER.info("Finished verifying {}",className);
    }
    
    /**
     * Include any packages after the base. Should include Neoforge in name if necessary
     */
    @SuppressWarnings("SameParameterValue")
    static String versionClassName(String name, String version) {
        return versionPackage(version)+"."+versionQuantify(name,version);
    }
    
    /**
     * ModLoader will always be Neoforge so we can cheat this a bit more than the CoreAPI implementation
     */
    static String versionPackage(String version) {
        String[] split = version.split("\\.");
        if(split.length<3) throw new RuntimeException("Can't parse package for unknown version "+version);
        return NEOFORGE_PKG+".v"+split[1]+".m"+split[2];
    }
    
    static String versionQuantify(String name, String version) {
        return name+version.replace('.','_');
    }
}