package mods.thecomputerizer.theimpossiblelibrary.forge.core.bootstrap;

import cpw.mods.modlauncher.Launcher;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncher;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.modules.ForgeModuleAccess;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.Objects;

import static java.lang.System.out;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.launcher;

public class TILLauncherForge {
    
    static final String FORGE_CORE = "mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader";
    static final String HACKS = "mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks";
    static final String MOD_LOADING = "mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.ForgeModLoading";
    static Logger LOGGER;
    
    static {
        out.println("Class init: "+thisClass().getName());
    }
    
    static ClassLoader bootLoader() {
        return java8() ? ClassLoader.getSystemClassLoader() : Launcher.class.getClassLoader();
    }
    
    @SuppressWarnings("UnusedReturnValue")
    private static Object callMethod(@Nullable Method method, @Nullable Object target, Object ... args) {
        if(Objects.isNull(method)) {
            LOGGER.error("Cannot call null method on {} with {}",target,args);
            return null;
        }
        try {
            return method.invoke(target,args);
        } catch(Throwable t) {
            LOGGER.error("Failed to invoke {} on {} with {}",method.getName(),target,args,t);
        }
        return null;
    }
    
    public static void checkInit(Class<?> caller) {
        if(Objects.isNull(launcher) && TILLauncher.init(false).isActiveForge()) {
            LOGGER = launcher.getLogger();
            if(bootLoader()!=thisClassLoader()) initBootLayerCoreAPI();
        } else if(Objects.nonNull(launcher)) {
            LOGGER = launcher.getLogger();
            if(Objects.nonNull(ForgeCoreLoader.initCoreAPI())) setModLoadingVersion(caller);
        } else out.println("Skipping Forge initialization for non Forge environment");
    }
    
    private static @Nullable Method getMethod(Class<?> c, String name, Class<?> ... argTypes) {
        try {
            return c.getDeclaredMethod(name,argTypes);
        } catch(Throwable declaredT) {
            LOGGER.debug("Declared method {} not found in {}! Checking for inherited method");
            try {
                return c.getMethod(name,argTypes);
            } catch(Throwable t) {
                LOGGER.error("Method {} not found in {} with args {}",name,c,argTypes,t);
                LOGGER.error("Declared method stacktrace",declaredT);
            }
        }
        return null;
    }
    
    private static boolean handleServiceEntryPoint() {
        if(java8()) {
            LOGGER.info("I see you are running Java 8. Good choice, but I'll be using burningwave anyways");
            try {
                Hacks.checkBurningWaveInit();
                ClassHelper.loadURL(bootLoader(),thisClass());
                return true;
            } catch(Throwable t) {
                LOGGER.fatal("Failed to handle Java 8 entrypoint??",t);
            }
            return false;
        }
        LOGGER.info("I see you are running Java 9+ so I'll be using burningwave to break its strong encapsulation");
        try {
            Object module = callMethod(getMethod(Class.class,"getModule"),TILServiceLauncherForge.class);
            if(Objects.isNull(module)) return false;
            Object moduleName = callMethod(getMethod(module.getClass(),"getName"),module);
            if(moduleName instanceof String) {
                Hacks.checkBurningWaveInit();
                ForgeModuleAccess.moveModule("SERVICE", "BOOT", (String)moduleName, true);
                LOGGER.info("Moved module {} to the BOOT layer",moduleName);
                return true;
            }
        } catch(Throwable t) {
            LOGGER.error("Entrypoint handling for the SERVICE layer failed!",t);
            return false;
        }
        LOGGER.error("Entrypoint handling for the SERVICE layer failed without a stacktrace??");
        return false;
    }
    
    private static void initBootLayerCoreAPI() {
        LOGGER.info("Handling SERVICE layer launcher");
        if(!handleServiceEntryPoint()) return;
        try {
            ClassLoader loader = bootLoader();
            callMethod(getMethod(Class.forName(HACKS,false,loader),"checkBurningWaveInit"),null);
            callMethod(getMethod(Class.forName(FORGE_CORE,false,loader),"initCoreAPI"),null);
            LOGGER.info("Successfully handled SERVICE layer initialized");
        } catch(Throwable t) {
            LOGGER.fatal("Failed to initialize BOOT layer CoreAPI instance",t);
        }
    }
    
    static boolean java8() {
        return System.getProperty("java.version").startsWith("1.");
    }
    
    public static void setModLoadingVersion(Class<?> caller) {
        try {
            Object ret = callMethod(getMethod(Class.forName(MOD_LOADING,false,bootLoader()),
                                              "setLoadingVersion",Class.class),null,caller);
            if(!Boolean.parseBoolean(String.valueOf(ret)))
                throw new RuntimeException("Failed to set mod loading version for "+caller);
        } catch(Throwable t) {
            LOGGER.fatal("Failed to set version for mod loading!",t);
        }
    }
    
    private static Class<?> thisClass() {
        return TILLauncherForge.class;
    }
    
    private static ClassLoader thisClassLoader() {
        return thisClass().getClassLoader();
    }
}