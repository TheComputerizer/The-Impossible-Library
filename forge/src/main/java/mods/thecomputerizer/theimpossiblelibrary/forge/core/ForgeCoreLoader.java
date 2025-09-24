package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import cpw.mods.modlauncher.Launcher;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleLayerAccess;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.bootstrap.TILLauncherForge;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ResolvedModuleAccess;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.modules.*;
import net.minecraftforge.forgespi.language.IModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks.CallStrategy.STATIC_DIRECT;

/**
 * Figures out which version to load on and how to load stuff on it
 *
 */
@SuppressWarnings("LoggingSimilarMessage")
public class ForgeCoreLoader {
    
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
     * Since the library interacts with GAME layer modules, we need to ensure it is available on the GAME layer
     */
    public static void addLibraryToGameLayer(String pkg, String gameLayerName) {
        ModuleClassLoaderAccess bootLoader = bootLoaderAccess();
        ModuleClassLoaderAccess gameLoader = ForgeModuleAccess.getModuleClassLoader("GAME");
        ResolvedModuleAccess module = bootLoader.getResolvedModule(pkg);
        Set<String> packages = module.packages(false);
        Object descriptor = gameLoader.getModuleDescriptorDirect(gameLayerName);
        ForgeModuleAccess.moveModule(bootLoader,gameLoader,module,true);
        TILLauncherForge.checkHacksInit(false,gameLoader.unwrap());
        addPackagesToDescriptor(module,packages,descriptor);
    }
    
    static void addPackagesToDescriptor(Object source, Set<String> packages, @Nullable Object descriptor) {
        if(Objects.isNull(descriptor)) {
            LOGGER.error("Cannot add packages from {} to null game layer module descriptor",source);
            return;
        }
        String target = Hacks.invoke(descriptor,"name");
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
        ClassLoader loader = Launcher.class.getClassLoader();
        return Objects.nonNull(loader) ? loader : ClassLoader.getSystemClassLoader();
    }
    
    static ModuleClassLoaderAccess bootLoaderAccess() {
        return ForgeModuleAccess.getModuleClassLoader(bootLoader(),"BOOT");
    }
    
    public static @Nullable Object getBootLoadedCoreAPI() {
        return getCoreAPIReflectively(bootLoader());
    }
    
    static Object getCoreAPIReflectively(ClassLoader loader) {
        try {
            return STATIC_DIRECT.get(Hacks.findClass(COREAPI_CLASS,loader),"INSTANCE");
        } catch(Throwable t) {
            LOGGER.debug("Failed to get CoreAPI instance on {}",loader);
        }
        return null;
    }
    
    public static <E extends Enum<E>> E getEnum(String className, ClassLoader loader, String name) {
        Class<?> foundClass = Hacks.findClassInHeirarchy(className,loader);
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
        LOGGER.debug("Starting CoreAPI init");
        Object bootInstance = getBootLoadedCoreAPI();
        if(Objects.nonNull(bootInstance)) {
            LOGGER.debug("Returning existing CoreAPI instance found in the BOOT layer");
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
    
    @IndirectCallers
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