package mods.thecomputerizer.theimpossiblelibrary.neoforge.core;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.IModuleLayerManager.Layer;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleLayerAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ResolvedModuleAccess;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.bootstrap.TILLauncherNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules.ModuleClassLoaderAccess;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules.NeoforgeModuleAccess;
import net.neoforged.neoforgespi.language.IModFileInfo;
import net.neoforged.neoforgespi.language.IModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.lang.module.ModuleDescriptor;
import java.util.*;

import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.BOOT;
import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.GAME;

/**
 * Figures out which version to load on and how to load stuff on it
 */
@SuppressWarnings("LoggingSimilarMessage")
public class NeoForgeCoreLoader {
    
    private static final String COREAPI_CLASS = "mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI";
    private static final String NEOFORGE_PKG = "mods.thecomputerizer.theimpossiblelibrary.neoforge";
    private static final Logger LOGGER = LogManager.getLogger("TIL NeoforgeCoreLoader");
    
    /**
     * Since the library interacts with GAME layer modules, we need to ensure it is available on the GAME layer
     */
    public static void addLibraryToGameLayer(String pkg, String gameLayerName) {
        ModuleClassLoaderAccess bootLoader = bootLoaderAccess();
        ModuleClassLoaderAccess gameLoader = NeoforgeModuleAccess.getModuleClassLoader(GAME);
        ResolvedModuleAccess module = bootLoader.getResolvedModule(pkg);
        Set<String> packages = module.packages(false);
        ModuleDescriptor descriptor = gameLoader.getModuleDescriptorDirect(gameLayerName);
        NeoforgeModuleAccess.moveModule(bootLoader,gameLoader,module,true);
        TILLauncherNeoForge.checkHacksInit(false,gameLoader.unwrap());
        addPackagesToDescriptor(module,packages,descriptor);
    }
    
    static void addPackagesToDescriptor(Object source, Set<String> packages, @Nullable ModuleDescriptor descriptor) {
        if(Objects.isNull(descriptor)) {
            LOGGER.error("Cannot add packages from {} to null game layer module descriptor",source);
            return;
        }
        String target = descriptor.name();
        if(packages.isEmpty()) {
            LOGGER.warn("Tried adding 0 packages from {} to game layer module {}",source,target);
            return;
        }
        LOGGER.info("Adding {} packages from {} to game layer module {}",packages.size(),source,target);
        Hacks.setFieldDirect(descriptor,"packages",packages);
    }
    
    /**
     * Should be the ClassLoader for the BOOT layer or the system ClassLoader if Java 8
     */
    public static ClassLoader bootLoader() {
        return Launcher.class.getClassLoader();
    }
    
    static ModuleClassLoaderAccess bootLoaderAccess() {
        return NeoforgeModuleAccess.getModuleClassLoader(BOOT);
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
    
    /**
     * Returns a CoreAPI instance on the BOOT layer ClassLoader. Initializes the source if necessary
     */
    public static @Nullable CoreAPI initCoreAPI() {
        return initCoreAPI(bootLoader());
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