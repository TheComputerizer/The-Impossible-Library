package mods.thecomputerizer.theimpossiblelibrary.api.core.loader;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;

import javax.annotation.Nullable;
import java.io.File;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URLClassLoader;
import java.util.*;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;

@Getter
public class MultiVersionModCandidate {
    
    public static File loaderFile;

    private final File file;
    private final Set<String> coreClassNames;
    private final Set<String> modClassNames;

    public MultiVersionModCandidate(String classpath) {
        this(Objects.nonNull(loaderFile) ? loaderFile : new File(Misc.getLastSplit(classpath,'.')+".class"));
    }

    public MultiVersionModCandidate(File file) {
        this.file = Objects.nonNull(file) ? file : new File(MODID+"-"+VERSION+".jar");
        this.coreClassNames = new HashSet<>();
        this.modClassNames = new HashSet<>();
    }

    public void addCoreClasses(String ... classes) {
        TILRef.logDebug("Registering {} coremod classes for file `{}` -> `{}`",classes.length,this.file,classes);
        this.coreClassNames.addAll(Arrays.asList(classes));
    }

    public void addModClasses(String ... classes) {
        TILRef.logDebug("Registering {} mod classes for file `{}` -> `{}`",classes.length,this.file,classes);
        this.modClassNames.addAll(Arrays.asList(classes));
    }

    public boolean canBeLoaded(@Nullable Class<?> clazz, Class<?> superClass, Class<? extends Annotation> annotation) {
        return Objects.nonNull(clazz) && superClass.isAssignableFrom(clazz) && clazz.isAnnotationPresent(annotation);
    }

    private @Nullable Class<?> findClass(ClassLoader classLoader, String name) {
        TILRef.logInfo("Locating loader class {}",name);
        try {
            return Class.forName(name,true,classLoader);
        } catch(ClassNotFoundException ignored) {}
        TILRef.logDebug("Attempting to add source for class that has not yet been loaded");
        try {
            ClassHelper.loadURL((URLClassLoader)classLoader,this.file.toURI().toURL());
        } catch(MalformedURLException | ClassCastException ex) {
            TILRef.logError("Error getting URL for source file `{}`!",this.file.getPath(),ex);
            return null;
        }
        TILRef.logDebug("Successfully added source! Reattempting to locate loader class");
        try {
            return Class.forName(name,true,classLoader);
        } catch(ClassNotFoundException ex) {
            TILRef.logDebug("Debug stacktrace",ex);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public void findCoreClasses(Map<MultiVersionModCandidate,Collection<Class<? extends CoreEntryPoint>>> classes,
                                MultiVersionModCandidate candidate, ClassLoader classLoader) {
        TILRef.logInfo("Finding coremod loader classes in file `{}`",this.file);
        for(String name : this.coreClassNames) {
            Class<?> clazz = findClass(classLoader,name);
            if(canBeLoaded(clazz,CoreEntryPoint.class,MultiVersionCoreMod.class)) {
                classes.putIfAbsent(candidate,new ArrayList<>());
                classes.get(candidate).add((Class<? extends CoreEntryPoint>)clazz);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public void findModClasses(Map<MultiVersionModCandidate,Collection<Class<? extends CommonEntryPoint>>> classes,
                               MultiVersionModCandidate candidate, ClassLoader classLoader) {
        TILRef.logInfo("Finding mod loader classes in file `{}`",this.file);
        for(String name : this.modClassNames) {
            Class<?> clazz = findClass(classLoader,name);
            if(canBeLoaded(clazz,CommonEntryPoint.class,MultiVersionMod.class)) {
                classes.putIfAbsent(candidate,new ArrayList<>());
                classes.get(candidate).add((Class<? extends CommonEntryPoint>)clazz);
            }
        }
    }
    
    public boolean hasCoreMods() {
        return !this.coreClassNames.isEmpty();
    }

    public boolean hasMods() {
        return !this.modClassNames.isEmpty();
    }
}