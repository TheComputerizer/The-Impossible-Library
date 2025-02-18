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
import static org.burningwave.core.assembler.StaticComponentContainer.Resources;

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
    
    /**
     * Deep check to handle ClassLoader issues
     */
    private boolean canBeAssigned(Class<?> clazz, Class<?> superClass) {
        if(superClass.isAssignableFrom(clazz)) return true;
        String superName = superClass.getName();
        Class<?> apparentSuper = clazz.getSuperclass();
        while(Objects.nonNull(apparentSuper)) {
            if(superName.equals(apparentSuper.getName())) return true;
            apparentSuper = apparentSuper.getSuperclass();
            if(Object.class.equals(apparentSuper)) break;
        }
        return false;
    }

    public boolean canBeLoaded(@Nullable Class<?> clazz, Class<?> superClass, Class<? extends Annotation> annotation) {
        return Objects.nonNull(clazz) && canBeAssigned(clazz,superClass) && checkAnnotation(clazz,annotation);
    }
    
    /**
     * Deep check to handle ClassLoader issues
     */
    private boolean checkAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        if(clazz.isAnnotationPresent(annotation)) return true;
        String name = annotation.getName();
        for(Annotation a : clazz.getAnnotations())
            if(name.equals(a.annotationType().getName())) return true;
        return false;
    }

    private @Nullable Class<?> findClass(ClassLoader loader, String name) {
        ClassLoader coreLoader = CoreAPI.class.getClassLoader();
        TILRef.logInfo("Locating loader class {} (for = {} | core = {})",name,loader,coreLoader);
        Class<?> clazz = ClassHelper.existsOn(name,coreLoader);
        if(Objects.nonNull(clazz)) return clazz;
        return ClassHelper.defineClass(coreLoader,name,Resources.get(ClassHelper.getResourcePath(name),loader));
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