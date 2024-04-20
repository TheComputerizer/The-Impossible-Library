package mods.thecomputerizer.theimpossiblelibrary.api.core;

import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class ReflectionHelper {

    public static final Lookup LOOKUP = MethodHandles.lookup();

    /**
     * Finds a class from the input name.
     * Returns null if the class does not exist.
     */
    public static @Nullable Class<?> findClass(String name) {
        try {
            return Class.forName(name);
        } catch(ClassNotFoundException ex) {
            TILRef.logError("Unable to find class with name `{}`",name);
            return null;
        }
    }

    /**
     * Finds a constructor of the given class with the specified args.
     * Returns null if the input class is null or the constructor does not exist.
     */
    public static @Nullable Constructor<?> findConstructor(@Nullable Class<?> clazz, Class<?> ... args) {
        if(Objects.isNull(clazz)) return null;
        try {
            return clazz.getConstructor(args);
        } catch(NoSuchMethodException ex) {
            TILRef.logError("Unable to find constructor of class `{}` with args `{}`",clazz,args);
            return null;
        }
    }

    /**
     * Finds a class from the input name that can be extended from the input superClass.
     * Returns null if the class does not exist.
     */
    public static @Nullable Class<?> findExtensibleClass(String name, Class<?> superClass) {
        Class<?> clazz = findClass(name);
        return Objects.nonNull(clazz) && superClass.isAssignableFrom(clazz) ? clazz : null;
    }

    public static MethodHandle findMethodHandle(Class<?> clazz, String name, Class<?> ... args) {
        try {
            Method method = clazz.getDeclaredMethod(name,args);
            method.setAccessible(true);
            return LOOKUP.unreflect(method);
        } catch (IllegalAccessException | NoSuchMethodException ex) {
            TILRef.logError("Unable to find method handle of name `{}` in class `{}` with args `{}`",name,clazz,args,ex);
            return null;
        }
    }

    public static @Nullable Field getField(@Nullable Class<?> clazz, String fieldName) {
        return Misc.applyNullable(clazz, c -> {
            try {
                return c.getDeclaredField(fieldName);
            } catch(NoSuchFieldException ex) {
                TILRef.logError("Could not find field of name {} in class {}!",fieldName,c);
                return null;
            }
        });
    }

    public static @Nullable Object getFieldInstance(@Nullable Field field) {
        return getFieldInstance(null,field);
    }

    public static @Nullable Object getFieldInstance(@Nullable Class<?> clazz, String fieldName) {
        return getFieldInstance(null,getField(clazz,fieldName));
    }

    public static @Nullable Object getFieldInstance(@Nullable Object parent, @Nullable Class<?> clazz, String fieldName) {
        return getFieldInstance(parent,getField(clazz,fieldName));
    }

    @SuppressWarnings("DataFlowIssue")
    public static @Nullable Object getFieldInstance(@Nullable Object parent, @Nullable Field field) {
        return Misc.applyNullable(field,f -> {
            try {
                if(!f.isAccessible()) f.setAccessible(true);
                return f.get(parent);
            } catch(IllegalAccessException ex) {
                TILRef.logError("Failed to retrieve instance of field {} of type {} from parent {} of class {}",
                        f, Misc.getNullable(f,f.getType(),"null"),parent, Misc.getNullable(parent,parent.getClass(),"null"));
                return null;
            }
        });
    }

    public static Class<?> getInnerClass(Class<?> clazz, @Nullable String name) {
        return Misc.applyNullable(name,s -> {
            for(Class<?> categoryClass : clazz.getDeclaredClasses())
                if(categoryClass.getSimpleName().matches(s)) return categoryClass;
            return null;
        });
    }

    public static @Nullable Method getMethod(@Nullable Class<?> clazz, String name, Class<?> ... argTypes) {
        return Misc.applyNullable(clazz,c -> {
            try {
                return c.getMethod(name,argTypes);
            } catch(NoSuchMethodException ex) {
                TILRef.logError("Failed to find method of name {} in class {} with args {}",name,clazz,argTypes);
                return null;
            }
        });
    }

    public static @Nullable Object invokeMethod(@Nullable Method method, Object invoker, Object ... args) {
        return Misc.applyNullable(method,m -> {
            try {
                return m.invoke(invoker,args);
            } catch(InvocationTargetException | IllegalAccessException | IllegalArgumentException ex) {
                TILRef.logError("Failed to invoke method {} with invoker {} and args {}",m,invoker,args,ex);
                return null;
            }
        });
    }
}
