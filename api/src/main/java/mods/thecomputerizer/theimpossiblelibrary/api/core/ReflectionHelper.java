package mods.thecomputerizer.theimpossiblelibrary.api.core;

import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Function;

@SuppressWarnings("unused") public class ReflectionHelper {

    public static final Lookup LOOKUP = MethodHandles.lookup();

    /**
     * Finds a constructor of the given class with the specified args.
     * Returns null if the input class is null or the constructor does not exist.
     */
    public static @Nullable Constructor<?> findConstructor(@Nullable Class<?> clazz, Class<?> ... args) {
        if(Objects.isNull(clazz)) return null;
        try {
            return clazz.getConstructor(args);
        } catch(NoSuchMethodException ex) {
            try {
                return clazz.getDeclaredConstructor(args);
            } catch(NoSuchMethodException ignored) {}
            TILRef.logError("Unable to find constructor of class `{}` with args `{}`",clazz,args);
            return null;
        }
    }

    /**
     * Finds a class from the input name that can be extended from the input superClass.
     * Returns null if the class does not exist.
     */
    public static @Nullable Class<?> findExtensibleClass(String name, Class<?> superClass) {
        Class<?> clazz = ClassHelper.findClass(name);
        return Objects.nonNull(clazz) && superClass.isAssignableFrom(clazz) ? clazz : null;
    }
    
    public static MethodHandle findMethodHandle(@Nullable String className, String name, Class<?> ... args) {
        return findMethodHandle(StringUtils.isBlank(className) ? null : ClassHelper.findClass(className),name,args);
    }

    public static MethodHandle findMethodHandle(@Nullable Class<?> clazz, String name, Class<?> ... args) {
        if(Objects.isNull(clazz)) {
            TILRef.logError("Cannot find method handle of null class!");
            return null;
        }
        try {
            Method method = clazz.getDeclaredMethod(name,args);
            method.setAccessible(true);
            return LOOKUP.unreflect(method);
        } catch (IllegalAccessException | NoSuchMethodException ex) {
            TILRef.logError("Unable to find method handle of name `{}` in class `{}` with args `{}`",name,clazz,args,ex);
            return null;
        }
    }
    
    public static @Nullable Field getField(@Nullable String className, String fieldName) {
        return getField(StringUtils.isBlank(className) ? null : ClassHelper.findClass(className),fieldName);
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
    
    public static @Nullable Method getMethod(@Nullable String className, String name, Class<?> ... argTypes) {
        return getMethod(StringUtils.isBlank(className) ? null : ClassHelper.findClass(className),name,argTypes);
    }

    public static @Nullable Method getMethod(@Nullable Class<?> clazz, String name, Class<?> ... argTypes) {
        return Misc.applyNullable(clazz,c -> {
            try {
                return c.getMethod(name,argTypes);
            } catch(NoSuchMethodException ignored) {
                try {
                    return c.getDeclaredMethod(name,argTypes);
                } catch(NoSuchMethodException ex) {
                    TILRef.logError("Failed to find method of name {} in class {} with args {}",name,clazz,argTypes);
                    return null;
                }
            }
        });
    }

    @SuppressWarnings("DataFlowIssue")
    public static @Nullable Object invokeMethod(@Nullable Method method, @Nullable Object invoker, Object ... args) {
        return Misc.applyNullable(method,m -> {
            try {
                if(!method.isAccessible()) method.setAccessible(true);
                return m.invoke(invoker,args);
            } catch(InvocationTargetException | IllegalAccessException | IllegalArgumentException ex) {
                TILRef.logError("Failed to invoke method {} with invoker {} and args {}",m,invoker,args,ex);
                return null;
            }
        });
    }
    
    public static @Nullable Object invokeMethod(@Nullable Class<?> clazz, String name, @Nullable Object invoker,
            Class<?>[] argTypes, Object ... args) {
        return invokeMethod(getMethod(clazz,name,argTypes),invoker,args);
    }
    
    public static @Nullable Object invokeMethod(@Nullable String className, String name,
            @Nullable Function<Class<?>,Object> invokerFunc, Class<?>[] argTypes, Object ... args) {
        if(StringUtils.isBlank(className)) {
            TILRef.logError("Tried to invoke method {} with null class name",name);
            return null;
        }
        Class<?> clazz = ClassHelper.findClass(className);
        return invokeMethod(clazz,name,Objects.nonNull(invokerFunc) ? invokerFunc.apply(clazz) : null,argTypes,args);
    }
    
    public static <T> @Nullable Object invokeStaticMethod(@Nullable Method method, Object ... args) {
        return invokeMethod(method,null,args);
    }
    
    public static <T> @Nullable Object invokeStaticMethod(@Nullable Class<T> clazz, String name, Class<?>[] argTypes,
            Object ... args) {
        return invokeMethod(clazz,name,null,argTypes,args);
    }
    
    public static @Nullable Object invokeStaticMethod(@Nullable String className, String name, Class<?>[] argTypes,
            Object ... args) {
        return invokeMethod(className,name,null,argTypes,args);
    }

    public static void setFieldValue(@Nullable Object parent, @Nullable Field field, @Nullable Object value) {
        if(Objects.isNull(field)) return;
        try {
            if(!field.isAccessible()) field.setAccessible(true);
            field.set(parent,value);
        } catch(IllegalAccessException ex) {
            boolean hasParent = Objects.nonNull(parent);
            Object[] args = hasParent ? new Object[]{field,parent,value,ex} : new Object[]{field,value,ex};
            TILRef.logError("Unable to set value of field {} "+(hasParent ? "in parent object {}}" : "")+" to {}",args);
        }
    }
}
