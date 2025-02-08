package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import cpw.mods.modlauncher.ArgumentHandler;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILForgeModLocator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

import static cpw.mods.modlauncher.Launcher.INSTANCE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;

public class ForgeCoreLoader {
    
    private static final Logger LOGGER = LogManager.getLogger("TIL ForgeCoreLoader");
    
    static ClassLoader coreLoader;
    static TILForgeModLocator locator;
    static boolean clientLaunch = true;
    static boolean queriedClient = true;
    
    /**
     * Get the command line argument handler in case we need to check stuff very early in the loading process
     */
    static ArgumentHandler getArgumentHandler() {
        LOGGER.info("Atempting to get ArgumentHandler for loader {}",Thread.currentThread().getContextClassLoader());
        Object args = getField(INSTANCE.getClass(),"argumentHandler",INSTANCE);
        if(!(args instanceof ArgumentHandler)) {
            TILRef.logError("Failed to find argument handler!");
            return null;
        }
        return (ArgumentHandler)args;
    }
    
    static @Nullable Object getField(Class<?> cls, String name, @Nullable Object instance) {
        try {
            Field field = cls.getDeclaredField(name);
            if(!field.isAccessible()) field.setAccessible(true);
            return field.get(instance);
        } catch(Exception ex) {
            LOGGER.error("Failed to get field {} from {} on instance {}",name,cls,instance,ex);
        }
        return null;
    }
    
    static String getVersionStr() {
        ArgumentHandler handler = getArgumentHandler();
        if(Objects.isNull(handler)) return null;
        String[] rawArgs = (String[])getField(handler.getClass(),"args",handler);
        if(Objects.isNull(rawArgs)) {
            LOGGER.error("Failed to find version using handler {}",handler);
            return null;
        }
        int versionIndex = -1;
        for(int i=0;i<rawArgs.length;i++) {
            if(rawArgs[i].equals("--fml.mcVersion")) {
                versionIndex = i+1;
                break;
            }
        }
        if(versionIndex>=0) {
            LOGGER.info("Found fml.mcVersion arg at index {} -> {}",versionIndex,rawArgs[versionIndex]);
            return rawArgs[versionIndex];
        }
        LOGGER.error("Failed to find version from {}", Arrays.toString(rawArgs));
        return null;
    }
    
    /**
     * Returns a CoreAPI instance on the input ClassLoader. Initializes the source if necessary
     */
    static @Nullable Object initCoreAPI(ClassLoader loader) {
        LOGGER.debug("Starting CoreAPI init");
        if(Objects.nonNull(CoreAPI.INSTANCE)) {
            LOGGER.debug("Returning existing CoreAPI instance");
            return CoreAPI.INSTANCE;
        }
        String version = getVersionStr();
        String coreName = CoreAPI.findLoadingClass(FORGE,version);
        LOGGER.debug("Attempting to initialize CoreAPI instance with name {} on {}",coreName,loader);
        Class<?> coreClass = null;
        try {
            coreClass = ClassHelper.findClass(coreName,loader);
        } catch(Exception ex) {
            LOGGER.error("Failed to load CoreAPI implementation {}",coreName,ex);
        }
        if(Objects.nonNull(coreClass)) {
            if(Objects.isNull(coreLoader)) coreLoader = coreClass.getClassLoader();
            Object instance = ClassHelper.initialize(coreClass);
            LOGGER.info("Initialized {} on {}",coreName,loader);
            return instance;
        }
        LOGGER.error("Failed to initialize {} on {}",coreName,loader);
        return null;
    }
    
    /**
     * Guess if we are running on the client side from the launch target. Defaults to true
     */
    public static boolean isClient() {
        if(queriedClient) return clientLaunch;
        queriedClient = true;
        clientLaunch = true;
        ArgumentHandler handler = getArgumentHandler();
        if(Objects.isNull(handler)) return true;
        String[] rawArgs = (String[])ReflectionHelper.getFieldInstance(handler,handler.getClass(),"args");
        if(Objects.isNull(rawArgs)) {
            LOGGER.error("Failed to find side using handler {}",handler);
            return true;
        }
        int versionIndex = -1;
        for(int i=0;i<rawArgs.length;i++) {
            if(rawArgs[i].equals("--launchTarget")) {
                versionIndex = i+1;
                break;
            }
        }
        if(versionIndex>=0) {
            LOGGER.info("Found launchTarget arg at index {} -> {}",versionIndex,rawArgs[versionIndex]);
            String target = rawArgs[versionIndex];
            clientLaunch = target.contains("client") || target.contains("Client") || target.contains("CLIENT");
            return clientLaunch;
        }
        LOGGER.error("Failed to query client side from {}", Arrays.toString(rawArgs));
        return true;
    }
}
