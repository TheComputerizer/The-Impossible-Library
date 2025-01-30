package mods.thecomputerizer.theimpossiblelibrary.forge.core;

import cpw.mods.modlauncher.ArgumentHandler;
import cpw.mods.modlauncher.TransformingClassLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.TILForgeModLocator;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static cpw.mods.modlauncher.Launcher.INSTANCE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;

public class ForgeCoreLoader {
    
    static final Set<ClassLoader> LOADERS = new HashSet<>(
            Collections.singletonList(ClassLoader.getSystemClassLoader()));
    static TILForgeModLocator locator;
    
    static boolean addURL(ClassLoader loader, URL url) {
        if(loader instanceof URLClassLoader) return ClassHelper.loadURL((URLClassLoader)loader, url);
        if(loader instanceof TransformingClassLoader) {
            Field field = ReflectionHelper.getField(TransformingClassLoader.class, "delegatedClassLoader");
            if(Objects.nonNull(field)) {
                Object instance = ReflectionHelper.getFieldInstance(loader,field);
                if(instance instanceof URLClassLoader) {
                    if(ClassHelper.loadURL((URLClassLoader)instance,url)) {
                        TILRef.logDebug("Successfully loaded URL to mod class loader {}",url);
                        return true;
                    } else TILRef.logError("Failed to load URL to mod class loader {}",url);
                } else TILRef.logError("delegatedClassLoader is not an instance of URLClassLoader??");
            } else TILRef.logError("Unable to find delegatedClassLoader field??");
        }
        return false;
    }
    
    static String getVersionStr() {
        Object args = ReflectionHelper.getFieldInstance(INSTANCE,INSTANCE.getClass(),"argumentHandler");
        if(!(args instanceof ArgumentHandler)) {
            TILRef.logError("Failed to find argument handler!");
            return null;
        }
        ArgumentHandler handler = (ArgumentHandler)args;
        String[] rawArgs = (String[])ReflectionHelper.getFieldInstance(handler,handler.getClass(),"args");
        if(Objects.isNull(rawArgs)) {
            TILRef.logError("Failed to find version using handler {}",handler);
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
            TILRef.logInfo("Found version arg at index {} -> {}",versionIndex,rawArgs[versionIndex]);
            return rawArgs[versionIndex];
        }
        TILRef.logError("Failed to find version from {}", Arrays.toString(rawArgs));
        return null;
    }
    
    /**
     * returns a CoreAPI instance that may or may not beloaded same ClassLoader as the caller
     */
    static @Nullable Object initCoreAPI() {
        URL source = ClassHelper.getSourceURL(MultiversionModLocator.class);
        ClassLoader pluginLoader = MultiversionModLocator.class.getClassLoader();
        String version = getVersionStr();
        String coreName = CoreAPI.findLoadingClass(FORGE, version);
        for(ClassLoader loader : LOADERS) {
            if(loader!=pluginLoader) {
                if(addURL(loader,source)) TILRef.logInfo("Added {} to {}", source, loader);
                else TILRef.logError("Failed to add {} to {}",source,loader);
            } else TILDev.logDebug("{} already present on {}", source, loader);
            Class<?> coreClass = ClassHelper.findClass(coreName,loader);
            if(Objects.nonNull(coreClass)) {
                Object instance = ClassHelper.initialize(coreClass);
                TILRef.logInfo("Initialized {} on {}",coreName,loader);
                return instance;
            }
            TILRef.logError("Failed to initialize {} on {}",coreName,loader);
        }
        return null;
    }
}
