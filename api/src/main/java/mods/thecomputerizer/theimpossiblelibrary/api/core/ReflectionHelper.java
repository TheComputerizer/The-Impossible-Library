package mods.thecomputerizer.theimpossiblelibrary.api.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.util.Objects;

/**
 * Temporary reimplementation for Music Triggers testing purposes
 */
public class ReflectionHelper {
    
    @IndirectCallers
    public static @Nullable Constructor<?> findConstructor(@Nullable Class<?> clazz, Class<?> ... args) {
        if(Objects.isNull(clazz)) {
            TILRef.logError("Cannot find constructor for null class!");
            return null;
        }
        try {
            return clazz.getConstructor(args);
        } catch(Throwable t) {
            TILRef.logError("Failed to find constructor for {} with args {}",clazz,args);
        }
        return null;
    }
    
    @IndirectCallers
    public static @Nullable Class<?> findExtensibleClass(String name, Class<?> superClass) {
        return ClassHelper.findExtensibleClass(name,superClass);
    }
}