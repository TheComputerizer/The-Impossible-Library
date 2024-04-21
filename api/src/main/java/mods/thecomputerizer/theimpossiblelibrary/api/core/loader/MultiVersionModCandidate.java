package mods.thecomputerizer.theimpossiblelibrary.api.core.loader;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;

import javax.annotation.Nullable;
import java.io.File;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;

@Getter
public class MultiVersionModCandidate {

    private final File file;
    private final Set<String> coreClassNames;
    private final Set<String> modClassNames;

    public MultiVersionModCandidate(String classpath) {
        this(new File(Misc.getLastSplit(classpath,'.')+".class"));
    }

    public MultiVersionModCandidate(File file) {
        this.file = Objects.nonNull(file) ? file : new File(TILRef.MODID+"-"+TILRef.VERSION+".jar");
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

    private @Nullable Class<?> findClass(Consumer<URL> sourceConsumer, String name) {
        TILRef.logInfo("Attempting to retrieve loader class `{}`",name);
        try {
            return Class.forName(name);
        } catch(ClassNotFoundException ex) {
            TILRef.logDebug("Debug stacktrace",ex);
        }
        TILRef.logInfo("Attempting to add source `{}` for class `{}` that was not previously loaded",
                this.file.getPath(),name);
        try {
            sourceConsumer.accept(this.file.toURI().toURL());
        } catch(MalformedURLException ex) {
            TILRef.logError("Error getting URL for source file `{}`!",this.file.getPath(),ex);
            return null;
        }
        TILRef.logInfo("Successfully added source! Reattempting to retrieve loader class");
        try {
            return Class.forName(name);
        } catch(Exception ex) {
            TILRef.logError("Failed to find retrieve class `{}`",name,ex);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public void findCoreClasses(Map<MultiVersionModCandidate,Collection<Class<? extends CoreEntryPoint>>> classes,
                                MultiVersionModCandidate candidate, Consumer<URL> sourceConsumer) {
        TILRef.logInfo("Finding coremod loader classes in file `{}`",this.file);
        for(String name : this.coreClassNames) {
            Class<?> clazz = findClass(sourceConsumer,name);
            if(canBeLoaded(clazz,CoreEntryPoint.class,MultiVersionCoreMod.class)) {
                classes.putIfAbsent(candidate,new ArrayList<>());
                classes.get(candidate).add((Class<? extends CoreEntryPoint>)clazz);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void findModClasses(Map<MultiVersionModCandidate,Collection<Class<? extends CommonEntryPoint>>> classes,
                               MultiVersionModCandidate candidate, Consumer<URL> sourceConsumer) {
        TILRef.logInfo("Finding mod loader classes in file `{}`",this.file);
        for(String name : this.modClassNames) {
            Class<?> clazz = findClass(sourceConsumer,name);
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