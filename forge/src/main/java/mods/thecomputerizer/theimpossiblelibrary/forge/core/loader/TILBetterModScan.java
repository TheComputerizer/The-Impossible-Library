package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import cpw.mods.modlauncher.TransformingClassLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraftforge.forgespi.language.ModFileScanData;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class TILBetterModScan extends ModFileScanData {
    
    private final Map<String,byte[]> writtenClasses;
    
    public TILBetterModScan() {
        super();
        this.writtenClasses = new HashMap<>();
    }
    
    public void addWrittenClass(String classpath, byte[] bytecode) {
        this.writtenClasses.put(classpath,bytecode);
    }
    
    public void defineClasses(ClassLoader loader) {
        loadCoreClass(loader);
        for(Entry<String,byte[]> entry : this.writtenClasses.entrySet()) {
            String classpath = entry.getKey();
            ClassHelper.defineClass(loader,classpath,entry.getValue());
            TILRef.logDebug("Successfully defined class {} for {}",classpath,loader);
        }
    }
    
    private void loadCoreClass(ClassLoader loader) {
        try {
            Class<?> systemClass = ClassLoader.getSystemClassLoader().loadClass(CoreAPI.class.getName());
            Object instance = ReflectionHelper.getFieldInstance(systemClass,"INSTANCE");
            if(Objects.nonNull(instance)) {
                //The classpath of the CoreAPI implementation extracted from the system classloader
                String coreImplClass = String.valueOf(instance).split(" ")[0];
                URL source = instance.getClass().getProtectionDomain().getCodeSource().getLocation();
                TILRef.logDebug("Found system CoreAPI implementation from {} with source {}",coreImplClass,source);
                loadCoreClass(loader,coreImplClass,source);
            } else TILRef.logFatal("System CoreAPI instance not found! Things will likely break soon");
        } catch(ClassNotFoundException ex) {
            TILRef.logError("Unable to sync CoreAPI instance from the system ClassLoader",ex);
        }
    }
    
    private void loadCoreClass(ClassLoader loader, String className, URL url) {
        if(loader instanceof TransformingClassLoader) {
            Field field = ReflectionHelper.getField(TransformingClassLoader.class,"delegatedClassLoader");
            if(Objects.nonNull(field)) {
                Object instance = ReflectionHelper.getFieldInstance(loader,field);
                if(instance instanceof URLClassLoader) {
                    ClassHelper.loadURL((URLClassLoader)instance,url);
                    TILRef.logDebug("Successfully loaded URL to mod class loader {}",url);
                    Class<?> clazz = ClassHelper.findClass(className,loader);
                    if(Objects.nonNull(clazz)) {
                        TILRef.logInfo("Successfully loaded CoreAPI instance {} to {}",clazz,clazz.getClassLoader());
                        CoreAPI.setInstance(clazz);
                    } else TILRef.logError("Failed to load CoreAPI class");
                } else TILRef.logError("delegatedClassLoader is not an instance of URLClassLoader??");
            } else TILRef.logError("Unable to find delegatedClassLoader field??");
        } else TILRef.logWarn("Loader does not seem to be the mod class loader so it will be ignored {}",loader);
    }
}