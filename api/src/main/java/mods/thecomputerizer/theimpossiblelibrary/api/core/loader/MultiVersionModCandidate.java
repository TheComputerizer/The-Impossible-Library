package mods.thecomputerizer.theimpossiblelibrary.api.core.loader;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.MultiVersionCoreMod;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.MultiVersionMod;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;

import javax.annotation.Nullable;
import java.io.File;
import java.lang.annotation.Annotation;
import java.util.*;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;

@Getter
public class MultiVersionModCandidate {
    
    public static File loaderFile;

    private final File file;
    private final Set<String> coreClassNames;
    private final Set<String> modClassNames;
    private boolean loaded;

    public MultiVersionModCandidate(String classpath) {
        this(Objects.nonNull(loaderFile) ? loaderFile : new File(Misc.getLastSplit(classpath,'.')+".class"));
    }

    public MultiVersionModCandidate(File file) {
        this.file = Objects.nonNull(file) ? file : new File(MODID+"-"+VERSION+".jar");
        this.coreClassNames = new HashSet<>();
        this.modClassNames = new HashSet<>();
    }

    public void addCoreClasses(Collection<String> foundCoreClasses, String ... classes) {
        TILRef.logDebug("Attempting to register {} coremod classes for file `{}` -> `{}`",
                        classes.length,this.file,classes);
        for(String className : classes) {
            if(foundCoreClasses.contains(className)) {
                TILRef.logDebug("Skipping already known core class {}",className);
                continue;
            }
            foundCoreClasses.add(className);
            this.coreClassNames.add(className);
        }
    }

    public void addModClasses(Collection<String> foundModClasses, String ... classes) {
        TILRef.logDebug("Attempting to register {} mod classes for file `{}` -> `{}`",
                        classes.length,this.file,classes);
        for(String className : classes) {
            if(foundModClasses.contains(className)) {
                TILRef.logDebug("Skipping already known mod class {}",className);
                continue;
            }
            foundModClasses.add(className);
            this.modClassNames.add(className);
        }
    }

    public boolean canBeLoaded(@Nullable Class<?> clazz, Class<?> superClass, Class<? extends Annotation> annotation) {
        return Objects.nonNull(clazz) && superClass.isAssignableFrom(clazz) && clazz.isAnnotationPresent(annotation);
    }

    private @Nullable Class<?> findClass(ClassLoader classLoader, String name, boolean loadSources) {
        TILRef.logInfo("Locating loader class {}",name);
        //if(!loadSources) this.loaded = true;
        if(!this.loaded) {
            TILRef.logInfo("Attempting to add source for class that has not yet been loaded");
            try {
                Class<?> systemClass = ClassHelper.findClass(name,ClassLoader.getSystemClassLoader());
                if(!CoreAPI.getInstance().addURLToClassLoader(classLoader,ClassHelper.getSourceURL(systemClass)))
                    TILRef.logFatal("Failed to load URL! The class {} will likely be broken for {}", name, classLoader);
            } catch(ClassCastException ex) {
                TILRef.logError("Error getting URL for {}!",name,ex);
                return null;
            }
            this.loaded = true;
        }
        TILRef.logInfo("Successfully added source! Reattempting to locate loader class");
        try {
            return Class.forName(name,true,classLoader);
        } catch(ClassNotFoundException ex) {
            TILRef.logInfo("Debug stacktrace",ex);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public void findCoreClasses(Map<MultiVersionModCandidate,Collection<Class<? extends CoreEntryPoint>>> classes,
                                MultiVersionModCandidate candidate, ClassLoader classLoader, boolean loadSources) {
        TILRef.logInfo("Finding coremod loader classes in file `{}`",this.file);
        for(String name : this.coreClassNames) {
            Class<?> clazz = findClass(classLoader,name,loadSources);
            if(canBeLoaded(clazz, CoreEntryPoint.class, MultiVersionCoreMod.class)) {
                classes.putIfAbsent(candidate,new ArrayList<>());
                classes.get(candidate).add((Class<? extends CoreEntryPoint>)clazz);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public void findModClasses(Map<MultiVersionModCandidate,Collection<Class<? extends CommonEntryPoint>>> classes,
                               MultiVersionModCandidate candidate, ClassLoader classLoader, boolean loadSources) {
        TILRef.logInfo("Finding mod loader classes in file `{}`",this.file);
        for(String name : this.modClassNames) {
            Class<?> clazz = findClass(classLoader,name,loadSources);
            if(canBeLoaded(clazz, CommonEntryPoint.class, MultiVersionMod.class)) {
                classes.putIfAbsent(candidate,new ArrayList<>());
                classes.get(candidate).add((Class<? extends CommonEntryPoint>)clazz);
            }
        }
    }
    
    @SuppressWarnings("unused") public boolean hasCoreMods() {
        return !this.coreClassNames.isEmpty();
    }

    public boolean hasMods() {
        return !this.modClassNames.isEmpty();
    }
}