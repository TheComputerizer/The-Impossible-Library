package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import cpw.mods.modlauncher.Launcher;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ClassAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ConfigurationAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleLayerAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleReferenceAccess;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.modules.ModuleReferenceHolder;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ResolvedModuleAccess;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.modules.ResolvedModuleHolder;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.modules.*;
import net.minecraftforge.forgespi.language.IModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static org.burningwave.core.assembler.StaticComponentContainer.Classes;
import static org.burningwave.core.assembler.StaticComponentContainer.ClassLoaders;
import static org.burningwave.core.assembler.StaticComponentContainer.Driver;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;

/**
 * Figures out which version to load on and how to load stuff on it
 *
 */
@SuppressWarnings("LoggingSimilarMessage")
public class ForgeCoreLoader {
    
    public static final boolean MODULE_LAYERS = Boolean.parseBoolean(System.getProperty("til.debug.forge.modules.layers","true"));
    private static final String API_PKG = "mods.thecomputerizer.theimpossiblelibrary.api";
    private static final String COREAPI_CLASS = API_PKG+".core.CoreAPI";
    private static final String FORGE_PKG = "mods.thecomputerizer.theimpossiblelibrary.forge";
    private static final String FORGE_CORE_PKG = FORGE_PKG+".core";
    private static final Logger LOGGER = LogManager.getLogger("TIL ForgeCoreLoader");
    public static final boolean SECURE_CLASSLOADER_FORMAT = newModuleClassLoaderFormat();
    
    private static boolean newModuleClassLoaderFormat() {
        if(isJava21()) return true;
        final String newFormatClassName = "net.minecraftforge.securemodules.SecureModuleClassLoader";
        try {
            Class<?> ignored = Class.forName(newFormatClassName);
            return true;
        } catch(Throwable ignored) {
            LOGGER.debug("Assuming 1.18.2-1.20.1 ModuleClassLoader format (missing {})",newFormatClassName);
        }
        return false;
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
        addModuleThouroughly(ref.getModule(),target.getModuleLayer(),name,ref.getReference(),target,finalizedPkgs);
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
     * Should be the ClassLoader for the BOOT layer or the system ClassLoader if Java 8
     */
    public static ClassLoader bootLoader() {
        ClassLoader loader = Launcher.class.getClassLoader();
        return Objects.nonNull(loader) ? loader : ClassLoader.getSystemClassLoader();
    }
    
    static ModuleClassLoaderAccess bootLoaderAccess() {
        return ForgeModuleAccess.getModuleClassLoader(bootLoader(),"BOOT");
    }
    
    /**
     * Export the given module to all packages loaded to a module in the GAME layer
     */
    public static void exportAllModules() {
        ForgeModuleAccess.exportAllPackages("BOOT","SERVICE","PLUGIN","GAME");
    }
    
    static Class<?> findClassInHeirarchy(ClassLoader loader, String className) {
        Class<?> foundClass = null;
        ClassLoader searchIn = loader;
        while(Objects.nonNull(searchIn)) {
            try {
                foundClass = Driver.getClassByName(className,false,loader,Classes.getClass());
            } catch(Throwable t) {
                LOGGER.debug("Class not found in ClassLoader {} (name = {})",searchIn,className);
            }
            if(Objects.nonNull(foundClass)) break;
            searchIn = ClassLoaders.getParent(searchIn);
        }
        if(Objects.isNull(foundClass)) {
            LOGGER.error("Class {} not found in ClassLoader heirarchy for {}",className,loader);
            return null;
        }
        return foundClass;
    }
    
    public static @Nullable Object getBootLoadedCoreAPI() {
        return getCoreAPIReflectively(bootLoader());
    }
    
    static Object getCoreAPIReflectively(ClassLoader loader) {
        try {
            return Fields.getStaticDirect(Class.forName(COREAPI_CLASS,false,loader),"INSTANCE");
        } catch(ClassNotFoundException ex) {
            LOGGER.debug("CoreAPI not found on {}",loader);
        }
        return null;
    }
    
    public static <E extends Enum<E>> E getEnum(ClassLoader loader, String className, String name) {
        Class<?> foundClass = findClassInHeirarchy(loader,className);
        return Objects.nonNull(foundClass) ? getEnum(foundClass,name) : null;
    }
    
    @SuppressWarnings("unchecked")
    static <E extends Enum<E>> E getEnum(Class<?> enumClass, String name) {
        if(Objects.isNull(name)) {
            LOGGER.error("Tried to get value of enum {} from null name!",enumClass);
            return null;
        }
        try {
            return Enum.valueOf((Class<E>)enumClass,name);
        } catch(Throwable t) {
            LOGGER.error("Failed to get enum {} of type {}",name,enumClass,t);
        }
        return null;
    }
    
    public static Object getLogger() {
        return LOGGER;
    }
    
    /**
     * Returns the index of the first matching element in the array or -1 if nothing matches
     */
    static <T> int getMatchingArrayIndex(T[] array, T value) {
        if(Objects.isNull(array) || array.length==0) return -1;
        for(int i=0;i<array.length;i++)
            if(value.equals(array[i])) return i;
        return -1;
    }
    
    static String getVersionFromForgeVersion(String forgeVersion) {
        String ignore = "forge-";
        String actualVersion = forgeVersion.startsWith(ignore) ? forgeVersion.substring(ignore.length()) : forgeVersion;
        String version = "1.21.1";
        if(actualVersion.startsWith("49.")) version = "1.20.4";
        else if(actualVersion.startsWith("50.")) version = "1.20.6";
        LOGGER.info("Guessed mc version {} from forge version {}",version,forgeVersion);
        return version;
    }
    
    static String getVersionStr() {
        ArgumentHandlerAccess handler = ForgeModuleAccess.getLauncher().argumentHandler();
        if(Objects.isNull(handler)) return null;
        String[] rawArgs = handler.getArgs();
        if(Objects.isNull(rawArgs)) {
            LOGGER.error("Failed to find version using handler {}",handler.access());
            return null;
        }
        //0 isntead of -1 if nothing matches
        int versionIndex = getMatchingArrayIndex(rawArgs,"--fml.mcVersion")+1;
        if(versionIndex>0) {
            LOGGER.debug("Found fml.mcVersion arg at index {} -> {}",versionIndex,rawArgs[versionIndex]);
            return rawArgs[versionIndex];
        }
        LOGGER.debug("--fml.mcVersion was not found so the mc version will be guessed from --version instead");
        versionIndex = getMatchingArrayIndex(rawArgs,"--version")+1;
        if(versionIndex>0) {
            LOGGER.debug("Found forge version arg at index {}",versionIndex);
            return getVersionFromForgeVersion(rawArgs[versionIndex]);
        }
        LOGGER.error("Failed to find fml.mcVersion or version flags from args {}",Arrays.toString(rawArgs));
        int javaVersion = 17;
        String mcVersion = "1.20.1";
        if(isJava8()) {
            javaVersion = 8;
            mcVersion = "1.16.5";
        } else if(isJava21()) {
            javaVersion = 21;
            mcVersion = "1.21.1";
        }
        LOGGER.warn("Guessing the current Minecraft version is {} since this is Java {}",mcVersion,javaVersion);
        return mcVersion;
    }
    
    static void handleDevLoading(int stage) {
        if(isJava8()) {
            LOGGER.debug("No dev load handling needed in Java 8");
            return;
        }
        final String appendArg = "(-Dtil.debug.forge.modules.layers=false)";
        Set<Enum<?>> completedLayers = ForgeModuleAccess.getModuleLayerHandler().completedLayers().keySet();
        switch(stage) {
            case 1: {
                LOGGER.debug("Skipping module layer movement hacks {}",appendArg);
                boolean bootLoaded = ForgeCoreLoader.class.getClassLoader()==bootLoader();
                LOGGER.debug("ForgeCoreLoader is boot loaded: {}",bootLoaded);
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
                LayerInfoAccess gameLayerInfo = ForgeModuleAccess.getLayerInfo("GAME");
                ModuleClassLoaderAccess gameLoader = gameLayerInfo.getModuleClassLoader();
                ModuleLayerAccess gameLayer = gameLayerInfo.getModuleLayer();
                ModuleAccess module = gameLayer.getModule(MODID);
                if(Objects.nonNull(module)) {
                    Set<String> allPackages = new HashSet<>();
                    for(String moduleName : new String[]{"main","tilforge"}) {
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
     * Returns a CoreAPI instance on the BOOT ClassLoader. Initializes the source if necessary
     */
    @IndirectCallers
    public static @Nullable Object initCoreAPI() {
        return initCoreAPI(bootLoader());
    }
    
    /**
     * Returns a CoreAPI instance on the input ClassLoader. Initializes the source if necessary
     */
    public static @Nullable Object initCoreAPI(ClassLoader loader) {
        Hacks.checkBurningWaveInit();
        LOGGER.info("Starting CoreAPI init");
        Object bootInstance = getBootLoadedCoreAPI();
        if(Objects.nonNull(bootInstance)) {
            LOGGER.info("Returning existing CoreAPI instance found in the BOOT layer");
            return bootInstance;
        }
        String version = getVersionStr();
        Class<?> coreClass = loadAPI(version);
        try {
            return coreClass.newInstance();
        } catch(InstantiationException | IllegalAccessException ex) {
            LOGGER.fatal("Caught reflection exception while trying to get CoreAPI instance as {}",coreClass,ex);
        } catch(Exception ex) {
            LOGGER.fatal("Unknown error while trying to get CoreAPI instance as {}",coreClass,ex);
        }
        LOGGER.fatal("Failed to initialize CoreAPI [Forge-{}] using {}",version,loader);
        return null;
    }
    
    public static boolean isJava8() {
        return System.getProperty("java.version").startsWith("1.");
    }
    
    public static boolean isJava21() {
        return System.getProperty("java.version").startsWith("21");
    }
    
    /**
     * Tries to get the ClassLoader instance associated with the given layer name
     */
    public static ClassLoader layerClassLoader(String layerName) {
        return ForgeModuleAccess.getModuleClassLoader(layerName).unwrap();
    }
    
    /**
     * Define necessary classes for the versioned CoreAPI instance
     * Returns the instance class
     */
    static Class<?> loadAPI(String version) {
        ClassLoader loader = bootLoader();
        String className = versionClassName("core.TILCoreForge",version);
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className,true,loader);
        } catch(Exception ex) {
            LOGGER.error("Failed to load class {} for {}",className,loader,ex);
        }
        if(Objects.isNull(clazz)) throw new RuntimeException("Failed to load CoreAPI instance [Forge-"+version+"]");
        else if(isJava8()) {
            String forgeModLoading = FORGE_CORE_PKG+".loader.ForgeModLoading";
            try {
                Class.forName(forgeModLoading,true,loader);
            } catch(Exception ex) {
                LOGGER.error("Failed to load class {} for {}",forgeModLoading,loader,ex);
            }
        }
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
            ModFileInfoAccess fileInfo = ForgeModuleAccess.getModFileInfo(mod.getOwningFile());
            ModuleClassLoaderAccess[] loaders = ForgeModuleAccess.getModuleClassLoaders("BOOT","SERVICE","PLUGIN");
            ModuleClassLoaderAccess targetLoader = ForgeModuleAccess.getModuleClassLoader(targetLayerName);
            Consumer<String> jarNameMismatchHandler = jarName -> nukeLoaderFields(jarName,loaders,targetLoader);
            ModuleReferenceHolder referenceHolder = fileInfo.getJarModule(targetLoader,modid,jarNameMismatchHandler);
            addModuleThouroughly(referenceHolder,modid,targetLoader,finalizedPkgs);
            LOGGER.info("Finished setting up {}",referenceHolder.getModule());
            
            //nuke & finalize
            nukeLoaderFields(modid,loaders);
            targetLoader.inheritClasses(referenceHolder.getModule(),new String[]{fileInfo.jarName(),modid},loaders);
            LOGGER.warn("------------------------------------------------------------------------------------------------");
            LOGGER.warn("SUCCESSFULLY LOADED {} TO THE {} LAYER HAVE A NICE DAY",modid,targetLayerName);
            LOGGER.warn("------------------------------------------------------------------------------------------------");
        } catch(Throwable t) {
            LOGGER.error("Failed to load new module!",t);
        }
    }
    
    /**
     * Add the module for the given package to the GAME layer and nuke all references to it from other layers
     */
    public static void nukeAndFinalize(IModInfo mod, String pkg, Set<String> finalizedPkgs) {
        ModuleClassLoaderAccess[] loaders = ForgeModuleAccess.getModuleClassLoaders("BOOT","SERVICE","PLUGIN");
        if(MODULE_LAYERS) {
            LOGGER.info("Finalizing package {}",pkg);
            ResolvedModuleHolder holder = ResolvedModuleHolder.findPackage(pkg,loaders);
            nukeAndFinalize(mod,holder,finalizedPkgs,true,loaders);
        } else handleDevLoading(4);
    }
    
    @SuppressWarnings("SameParameterValue")
    private static void nukeAndFinalize(IModInfo mod, ResolvedModuleHolder holder, Set<String> finalizedPkgs,
            boolean bigLog, ModuleClassLoaderAccess ... loaders) {
        if(Objects.isNull(holder)) {
            loadNewModuleTo(mod,"GAME",finalizedPkgs);
            return;
        }
        ResolvedModuleAccess resolvedModule = holder.getModule();
        String name = resolvedModule.name();
        LOGGER.debug("Got resolved module as {}({})",resolvedModule,name);
        if(bigLog) {
            LOGGER.warn("------------------------------------------------------------------------------------------------");
            LOGGER.warn("NUKING ALL REFERENCES OF MODULE {} FROM THE BOOT, SERVICE, & PLUGIN LAYERS",name);
            LOGGER.warn("------------------------------------------------------------------------------------------------");
        } else LOGGER.debug("Nuking BOOT, SERVICE, & PLUGIN layer references to module {}",name);
        ModuleClassLoaderAccess foundLoader = holder.getLoader();
        ModuleAccess module = foundLoader.getModuleLayer().getModule(name);
        ModuleClassLoaderAccess target = ForgeModuleAccess.getModuleClassLoader("GAME");
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
    
    /**
     * Java 8 doesn't have modules, so move all classes loaded from the source of the given package to the target
     * ClassLoader and things should work fine.
     * Requires generated classes to be excluded from source searching.
     */
    public static void nukeAndFinalizeJava8(Set<Class<?>> getSourcesFrom, ClassLoader target, boolean first) {
        if(getSourcesFrom.isEmpty()) {
            LOGGER.error("No classes to get sources from!");
            return;
        }
        Set<String> sources = new HashSet<>();
        for(Class<?> from : getSourcesFrom) ClassHelper.addSource(sources,from);
        CoreAPI core = CoreAPI.getInstance();
        if(first) core.addSources(sources);
        LOGGER.info("Adding {} sources to target loader {}",sources.size(),target);
        sources.forEach(source -> {
            LOGGER.info("Adding source {}",source);
            core.addURLToClassLoader(target,source);
        });
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
    
    /**
     * Since this class is intially loaded in the SERVICE layer which has BOOT as a parent separate from PLUGIN,
     * we need a workaround for the PLUGIN layer thinking there are duplicate modules.
     * This is needed since IModLanguageProvider implementations are forced into PLUGIN layer from service loading and
     * can likely only be called via reflection.
     */
    public static void resyncModules(ClassLoader loaderTo, String layerTo, ClassLoader loaderFrom) {
        if(isJava8()) return; //Not needed on Java 8
        if(!MODULE_LAYERS) { //Resolve differently in dev since the modules are initially loaded in the BOOT layer
            handleDevLoading(3);
            return;
        }
        resyncModules(ForgeModuleAccess.getModuleClassLoader(loaderTo), layerTo,
                      ForgeModuleAccess.getModuleClassLoader(loaderFrom));
    }
    
    private static void resyncModules(ModuleClassLoaderAccess loaderTo, String layerTo,
            ModuleClassLoaderAccess loaderFrom) {
        LOGGER.info("Resyncing module to {}",layerTo);
        if(!"PLUGIN".equals(layerTo)) loaderFrom.configuration().addModule(loaderFrom.getResolvedModule(FORGE_CORE_PKG));
        
        //Remove module from PLUGIN layer
        ResolvedModuleAccess resolvedModule = loaderTo.getResolvedModule(FORGE_CORE_PKG);
        String name = resolvedModule.name();
        ConfigurationAccess configuration = loaderTo.configuration();
        configuration.removeModule(resolvedModule);
        
        //Finalize by dealing with the module layers & fixing parent loaders
        ForgeModuleAccess.getModuleLayer(layerTo).removeModule(name);
        loaderTo.addParentLoaders(resolvedModule.packages(),loaderFrom);
        loaderTo.removeRoot(name);
        
        loaderTo.removePackagesForModule(resolvedModule); //Prevent reading duplicate modules
    }
    
    public static void sanityCheckModule(Class<?> c, String name) {
        ClassAccess access = ForgeModuleAccess.getClassAccess(c);
        if(Objects.isNull(access)) {
            LOGGER.error("Failed to get ClassAccess for {}! Cannot run sanity check",c);
            return;
        }
        String moduleName = access.getModuleName();
        if(!name.equals(moduleName)) {
            //By this point the class is definitely in the GAME layer regardless of whether the module is correct
            ForgeModuleAccess.setClassModule(access,"GAME",name);
            LOGGER.info("Moved {} from module {} to module {}",c,moduleName,name);
        }
    }
    
    public static void verifyModule(String className, IModInfo info, Object moduleLayer) {
        LOGGER.info("Verifying that {} is valid for {} and can be found in {}",className,info,moduleLayer);
        String modid = info.getModId();
        ModFileInfoAccess fileInfo = ForgeModuleAccess.getModFileInfo(info.getOwningFile());
        String moduleName = fileInfo.moduleName();
        if(!modid.equals(moduleName)) LOGGER.error("Mod id {} does not equal module name {}!",modid,moduleName);
        ModuleLayerAccess layerAccess = ForgeModuleAccess.getModuleLayer(moduleLayer);
        Optional<Object> optionalModule = layerAccess.findModule(moduleName);
        if(!optionalModule.isPresent()) {
            layerAccess.findAndAddModule(moduleLayer,moduleName,modid);
            optionalModule = layerAccess.findModule(moduleName);
        }
        if(optionalModule.isPresent())
            ForgeModuleAccess.getModule(optionalModule.get()).addClassIfMissing(className,layerAccess);
        else LOGGER.error("Module {} is not present in the target layer!",moduleName);
        LOGGER.info("Finished verifying {}",className);
    }
    
    /**
     * Include any packages after the base. Should include Forge in name if necessary
     */
    @SuppressWarnings("SameParameterValue")
    static String versionClassName(String name, String version) {
        return versionPackage(version)+"."+versionQuantify(name,version);
    }
    
    /**
     * ModLoader will always be Forge so we can cheat this a bit more than the CoreAPI implementation
     */
    static String versionPackage(String version) {
        String[] split = version.split("\\.");
        if(split.length<3) throw new RuntimeException("Can't parse package for unknown version "+version);
        return FORGE_PKG+".v"+split[1]+".m"+split[2];
    }
    
    static String versionQuantify(String name, String version) {
        return name+version.replace('.','_');
    }
}