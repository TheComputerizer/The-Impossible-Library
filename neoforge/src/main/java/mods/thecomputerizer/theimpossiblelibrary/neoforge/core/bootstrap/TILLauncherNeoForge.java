package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.bootstrap;

import cpw.mods.modlauncher.Launcher;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncher;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.NeoForgeCoreLoader;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules.NeoforgeModuleAccess;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.Objects;

import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.BOOT;
import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.SERVICE;
import static java.lang.System.out;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.launcher;

public class TILLauncherNeoForge {
    
    static final String NEOFORGE_CORE = "mods.thecomputerizer.theimpossiblelibrary.neoforge.core.NeoForgeCoreLoader";
    static final String HACKS = "mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks";
    static final String MOD_LOADING = "mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.NeoForgeModLoading";
    static Logger LOGGER;
    
    static {
        out.println("Class init: "+thisClass().getName());
    }
    
    private static ClassLoader bootLoader() {
        return Launcher.class.getClassLoader();
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
        if(Objects.isNull(launcher) && TILLauncher.init(false).isActiveNeoforge()) {
            LOGGER = launcher.getLogger();
            if(bootLoader()!=thisClassLoader()) initBootLayerCoreAPI();
        } else if(Objects.nonNull(launcher)) {
            LOGGER = launcher.getLogger();
            if(Objects.nonNull(NeoForgeCoreLoader.initCoreAPI())) setModLoadingVersion(caller);
        } else out.println("Skipping Neoforge initialization for non Neoforge environment");
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
        LOGGER.info("I see you are running Java 9+ so I'll be using burningwave to break its strong encapsulation");
        try {
            Hacks.checkBurningWaveInit();
            String moduleName = thisClass().getModule().getName();
            NeoforgeModuleAccess.moveModule(SERVICE,BOOT,moduleName,true);
            LOGGER.info("Moved module {} to the BOOT layer",moduleName);
            return true;
        } catch(Throwable t) {
            LOGGER.error("Entrypoint handling for the SERVICE layer failed!",t);
            return false;
        }
    }
    
    private static void initBootLayerCoreAPI() {
        LOGGER.info("Handling SERVICE layer launcher");
        if(!handleServiceEntryPoint()) return;
        try {
            ClassLoader loader = bootLoader();
            callMethod(getMethod(Class.forName(HACKS,false,loader),"checkBurningWaveInit"),null);
            callMethod(getMethod(Class.forName(NEOFORGE_CORE, false, loader), "initCoreAPI"), null);
            LOGGER.info("Successfully handled SERVICE layer initialized");
        } catch(Throwable t) {
            LOGGER.fatal("Failed to initialize BOOT layer CoreAPI instance",t);
        }
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
        return TILLauncherNeoForge.class;
    }
    
    private static ClassLoader thisClassLoader() {
        return thisClass().getClassLoader();
    }
}