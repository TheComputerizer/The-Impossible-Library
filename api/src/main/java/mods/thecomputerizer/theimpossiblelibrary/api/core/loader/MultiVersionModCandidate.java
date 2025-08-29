package mods.thecomputerizer.theimpossiblelibrary.api.core.loader;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.MultiVersionCoreMod;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.MultiVersionMod;
import mods.thecomputerizer.theimpossiblelibrary.api.io.FileHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;

@Getter
public class MultiVersionModCandidate {
    
    @Getter private static File loaderFile;
    
    static File fromClassName(String className) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String asPath = ClassHelper.getResourcePath(className);
        try {
            return FileHelper.get(ClassHelper.absoluteLocation(loader.getResource(asPath),asPath));
        } catch(Exception ex) {
            TILRef.logError("Can't find file for {}",className,ex);
        }
        return null;
    }
    
    public static void setLoaderFile(File file) {
        loaderFile = file;
        if(DEV) TILDev.setDevPaths(file.toPath());
    }
    
    public static void setLoaderPath(Path path) {
        loaderFile = path.toFile();
        if(DEV) TILDev.setDevPaths(path);
    }

    private final CoreAPI core;
    private final boolean classpath;
    private final String relativePath;
    private final File file;
    private final URL source;
    private final Set<String> coreClassNames;
    private final Set<String> modClassNames;

    public MultiVersionModCandidate(CoreAPI core, String className) {
        this(core,fromClassName(className),className,true);
    }
    
    public MultiVersionModCandidate(CoreAPI core, File file) {
        this(core,file,file.getAbsolutePath(),false);
    }

    MultiVersionModCandidate(CoreAPI core, File file, String relativePath, boolean classpath) {
        this.core = core;
        this.classpath = classpath;
        this.relativePath = relativePath;
        this.file = Objects.nonNull(file) ? file : new File(MODID+"-"+VERSION+".jar");
        this.source = FileHelper.toURL(this.file);
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
        CoreAPI core = CoreAPI.getInstance();
        ModLoader modLoader = core.getModLoader();
        if(modLoader.isNeoForge() || (modLoader.isForge() && !CoreAPI.isV16())) {
            String path = ClassHelper.getResourcePath(name);
            URL source = this.classpath ? loader.getResource(path) :
                    ClassHelper.getJarResource(this.file.getAbsolutePath(),path);
            return ClassHelper.defineClass(coreLoader,name,source);
        }
        URL source = this.classpath ? ClassHelper.getSourceURL(name,loader) : this.source;
        core.addURLToClassLoader(loader,source);
        TILRef.logInfo("Added URL {} to loader {}",source,loader);
        return ClassHelper.findClass(name,loader);
    }

    public void findCoreClasses(Map<MultiVersionModCandidate,Collection<Class<? extends CoreEntryPoint>>> classes,
                                MultiVersionModCandidate candidate, ClassLoader classLoader) {
        TILRef.logInfo("Finding coremod loader classes in file `{}`",this.file);
        Collection<Class<? extends CoreEntryPoint>> found = new ArrayList<>();
        findCoreClasses(found,classLoader);
        if(found.isEmpty()) return;
        classes.putIfAbsent(candidate,new ArrayList<>());
        classes.get(candidate).addAll(found);
    }
    
    public void findCoreClasses(Collection<Class<? extends CoreEntryPoint>> classes, ClassLoader classLoader) {
        for(String name : this.coreClassNames) {
            Class<?> clazz = findClass(classLoader,name);
            if(canBeLoaded(clazz,CoreEntryPoint.class,MultiVersionCoreMod.class))
                classes.add(GenericUtils.cast(clazz));
        }
    }
    
    public void findModClasses(Map<MultiVersionModCandidate,Collection<Class<? extends CommonEntryPoint>>> classes,
                               MultiVersionModCandidate candidate, ClassLoader classLoader) {
        TILRef.logInfo("Finding mod loader classes in file `{}`",this.file);
        Collection<Class<? extends CommonEntryPoint>> found = new ArrayList<>();
        findModClasses(found,classLoader);
        if(found.isEmpty()) return;
        classes.putIfAbsent(candidate,new ArrayList<>());
        classes.get(candidate).addAll(found);
    }
    
    public void findModClasses(Collection<Class<? extends CommonEntryPoint>> classes, ClassLoader classLoader) {
        for(String name : this.modClassNames) {
            Class<?> clazz = findClass(classLoader,name);
            if(canBeLoaded(clazz,CommonEntryPoint.class,MultiVersionMod.class))
                classes.add(GenericUtils.cast(clazz));
        }
    }
    
    public boolean hasCoreMods() {
        return !this.coreClassNames.isEmpty();
    }

    public boolean hasMods() {
        return !this.modClassNames.isEmpty();
    }
    
    /**
     * CoreMods still need to be handled separately, but NeoForge has good entry hooks for custom mod loading.
     * In this case it is better to merge the candidates for preloading without needing to store anything statically.
     */
    public void merge(MultiVersionModCandidate otherCandidate) {
        this.coreClassNames.addAll(otherCandidate.coreClassNames);
        this.modClassNames.addAll(otherCandidate.modClassNames);
    }
}