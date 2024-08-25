package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import cpw.mods.modlauncher.TransformingClassLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class TILBetterModScan extends ModFileScanData {
    
    private final Map<String,MultiVersionModInfo> modInfos;
    private final Map<String,byte[]> writtenClasses;
    
    public TILBetterModScan() {
        super();
        this.modInfos = new HashMap<>();
        this.writtenClasses = new HashMap<>();
    }
    
    public void addWrittenClass(String classpath, MultiVersionModInfo info, byte[] bytecode) {
        this.modInfos.put(classpath,info);
        this.writtenClasses.put(classpath,bytecode);
    }
    
    public void defineClasses(ClassLoader ... loaders) {
        loadSources(loaders);
        for(Entry<String,byte[]> entry : this.writtenClasses.entrySet()) {
            String classpath = entry.getKey();
            Class<?> entryClass = this.modInfos.get(classpath).getEntryClass();
            ClassLoader entryLoader = entryClass.getClassLoader();
            for(ClassLoader loader : loaders) {
                if(loader!=entryLoader) {
                    //Ensures that all extensions of CommonEntryPoint referenced by the written mod class
                    //will be loaded in the context of the mod class loader
                    ClassHelper.syncSourcesAndLoadClass(entryLoader,loader,entryClass.getName());
                }
                ClassHelper.resolveClass(loader,ClassHelper.defineClass(loader,classpath,entry.getValue()));
                TILRef.logDebug("Successfully defined and resolved class {} for {}",classpath,loader);
            }
        }
    }
    
    private void loadSources(ClassLoader ... loaders) {
        try {
            Class<?> systemClass = ClassLoader.getSystemClassLoader().loadClass(CoreAPI.class.getName());
            Object instance = ReflectionHelper.getFieldInstance(systemClass,"INSTANCE");
            if(Objects.nonNull(instance)) {
                //The classpath of the CoreAPI implementation extracted from the system classloader
                String coreImplClass = String.valueOf(instance).split(" ")[0];
                syncCoreAPI(coreImplClass,ClassHelper.getSourceURL(systemClass),loaders);
                
            } else TILRef.logFatal("System CoreAPI instance not found! Things will likely break soon");
        } catch(ClassNotFoundException ex) {
            TILRef.logError("Unable to sync CoreAPI instance from the system ClassLoader",ex);
        }
    }
    
    /**
     * The CoreAPI instance isn't guarunteed to exist here due to class loading shenanigans, so it would not be a good
     * idea to rely on CoreAPI#addURLToClassLoader
     */
    private void syncCoreAPI(String className, URL url, ClassLoader ... loaders) {
        for(ClassLoader loader : loaders) {
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
                            return;
                        } else TILRef.logError("Failed to load CoreAPI class");
                    } else TILRef.logError("delegatedClassLoader is not an instance of URLClassLoader??");
                } else TILRef.logError("Unable to find delegatedClassLoader field??");
            }
        }
    }
}