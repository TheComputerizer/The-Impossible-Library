package mods.thecomputerizer.theimpossiblelibrary.api.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import org.jetbrains.annotations.Nullable;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.function.Function;

import static java.lang.reflect.Modifier.FINAL;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;

/**
 * This class is obselete now, but it deals with "normal" non-burningwave reflection.
 * Once all the source set & runtime issues are fixed this will likely non be needed anymore
 */
public class ReflectionHelper {

    public static final Lookup LOOKUP = MethodHandles.lookup();

    /**
     * Finds a constructor of the given class with the specified args.
     * Returns null if the input class is null or the constructor does not exist.
     */
    @IndirectCallers
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
    
    @IndirectCallers
    public static MethodHandle findMethodHandle(@Nullable String className, String name, Class<?> ... args) {
        return findMethodHandle(TextHelper.isBlank(className) ? null : ClassHelper.findClass(className), name, args);
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
        return getField(TextHelper.isBlank(className) ? null : ClassHelper.findClass(className),fieldName);
    }

    public static @Nullable Field getField(@Nullable Class<?> clazz, String fieldName) {
        return Misc.applyNullable(clazz, c -> {
            try {
                return c.getField(fieldName);
            } catch(NoSuchFieldException ignored) {
                try {
                    return c.getDeclaredField(fieldName);
                } catch(NoSuchFieldException ex) {
                    TILRef.logError("Could not find field of name {} in class {}!",fieldName,c,ex);
                    return null;
                }
            }
        });
    }

    public static <T> @Nullable T getFieldInstance(@Nullable Field field) {
        return getFieldInstance(null,field);
    }
    
    public static <T> @Nullable T getFieldInstance(@Nullable Class<?> clazz, String fieldName) {
        return getFieldInstance(null,getField(clazz,fieldName));
    }
    
    public static <T> @Nullable T getFieldInstance(@Nullable Object parent, @Nullable Class<?> clazz, String fieldName) {
        return getFieldInstance(parent, getField(clazz, fieldName));
    }
    
    @SuppressWarnings({"unchecked", "DataFlowIssue"})
    public static <T> @Nullable T getFieldInstance(@Nullable Object parent, @Nullable Field field) {
        return (T)Misc.applyNullable(field,f -> {
            try {
                if(!f.isAccessible()) f.setAccessible(true);
                return f.get(parent);
            } catch(IllegalAccessException ex) {
                TILRef.logError("Failed to retrieve instance of field {} of type {} from parent {} of class {}",
                        f,Misc.getNullable(f,f.getType(),"null"),parent,
                                Misc.getNullable(parent,parent.getClass(),"null"));
                return null;
            }
        });
    }
    
    @IndirectCallers
    public static Class<?> getInnerClass(Class<?> clazz, @Nullable String name) {
        return Misc.applyNullable(name,s -> {
            for(Class<?> categoryClass : clazz.getDeclaredClasses())
                if(categoryClass.getSimpleName().matches(s)) return categoryClass;
            return null;
        });
    }
    
    public static @Nullable Field getMappedField(@Nullable Class<?> clazz, String named, String intermediary,
            @Nullable Class<?> desc) {
        return getMappedField(clazz,DEV ? named : intermediary,desc);
    }
    
    public static @Nullable Field getMappedField(@Nullable Class<?> clazz, String fieldName, @Nullable Class<?> desc) {
        if(Objects.isNull(clazz) || Objects.isNull(desc)) return null;
        String descName = "L"+desc.getName().replace('.','/')+";";
        return getField(clazz,CoreAPI.getInstance().mapFieldName(clazz.getName(),fieldName,descName));
    }
    
    public static <T> @Nullable T getMappedFieldInstance(@Nullable Class<?> clazz, String named, String intermediary,
            @Nullable Class<?> desc) {
        return getMappedFieldInstance(null,clazz,DEV ? named : intermediary,desc);
    }
    
    @IndirectCallers
    public static <T> @Nullable T getMappedFieldInstance(@Nullable Class<?> clazz, String fieldName,
            @Nullable Class<?> desc) {
        return getMappedFieldInstance(null,clazz,fieldName,desc);
    }
    
    @IndirectCallers
    public static <T> @Nullable T getMappedFieldInstance(@Nullable Object parent, @Nullable Class<?> clazz,
            String named, String intermediary, @Nullable Class<?> desc) {
        return getMappedFieldInstance(parent,clazz,DEV ? named : intermediary,desc);
    }
    
    public static <T> @Nullable T getMappedFieldInstance(@Nullable Object parent, @Nullable Class<?> clazz,
            String fieldName, @Nullable Class<?> desc) {
        return getFieldInstance(parent,getMappedField(clazz,fieldName,desc));
    }
    
    @IndirectCallers
    public static @Nullable Method getMethod(@Nullable String className, String name, Class<?> ... argTypes) {
        return getMethod(TextHelper.isBlank(className) ? null : ClassHelper.findClass(className),name,argTypes);
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
    
    @SuppressWarnings("unchecked")
    public static <T> @Nullable T invokeHandle(@Nullable MethodHandle handle, @Nullable Object invoker, Object ... args) {
        if(Objects.isNull(handle)) TILDev.logInfo("Trying to invoke null method handle");
        return (T)Misc.applyNullable(handle,mh -> {
            try {
                return mh.invoke(invoker,args);
            } catch(Throwable t) {
                TILRef.logError("Failed to invoke method handle {} with invoker {} and args {}",mh,invoker,args,t);
                return null;
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    public static <T> @Nullable T invokeMethod(@Nullable Method method, @Nullable Object invoker, Object ... args) {
        if(Objects.isNull(method)) TILDev.logInfo("Trying to invoke null method");
        return (T)Misc.applyNullable(method,m -> {
            try {
                if(!m.isAccessible()) m.setAccessible(true);
                return m.invoke(invoker,args);
            } catch(InvocationTargetException | IllegalAccessException | IllegalArgumentException ex) {
                TILRef.logError("Failed to invoke method {} with invoker {} and args {}",m,invoker,args,ex);
                return null;
            }
        });
    }
    
    public static <T> @Nullable T invokeMethod(@Nullable Class<?> clazz, String name, @Nullable Object invoker,
            Class<?>[] argTypes, Object ... args) {
        return invokeMethod(getMethod(clazz,name,argTypes),invoker,args);
    }
    
    public static <T> @Nullable T invokeMethod(@Nullable String className, String name,
            @Nullable Function<Class<?>,Object> invokerFunc, Class<?>[] argTypes, Object ... args) {
        if(TextHelper.isBlank(className)) {
            TILRef.logError("Tried to invoke method {} with null class name",name);
            return null;
        }
        Class<?> clazz = ClassHelper.findClass(className);
        return invokeMethod(clazz,name,Objects.nonNull(invokerFunc) ? invokerFunc.apply(clazz) : null,argTypes,args);
    }
    
    @IndirectCallers
    public static <T> @Nullable T invokeStaticHandle(@Nullable MethodHandle handle, Object ... args) {
        return invokeHandle(handle,null,args);
    }
    
    @IndirectCallers
    public static <T> @Nullable T invokeStaticMethod(@Nullable Method method, Object ... args) {
        return invokeMethod(method,null,args);
    }
    
    public static <T> @Nullable T invokeStaticMethod(@Nullable Class<T> clazz, String name, Class<?>[] argTypes,
            Object ... args) {
        return invokeMethod(clazz,name,null,argTypes,args);
    }
    
    @IndirectCallers
    public static <T> @Nullable T invokeStaticMethod(@Nullable String className, String name, Class<?>[] argTypes,
            Object ... args) {
        return invokeMethod(className,name,null,argTypes,args);
    }
    
    /**
     * We do a little trolling
     */
    public static void modifyFinalField(@Nullable Object parent, @Nullable Field field, @Nullable Object value) {
        if(Objects.isNull(field)) return;
        int modifiers = field.getModifiers();
        boolean isFinal = Modifier.isFinal(modifiers);
        if(isFinal) setFieldModifiers(field,modifiers & ~FINAL); //Yep, you can do that
        setFieldValue(parent,field,value);
        if(isFinal) setFieldModifiers(field,modifiers); //Change it back since fields are usually final for a reason
    }
    
    @IndirectCallers
    public static void setFieldInstance(Class<?> clazz, String name, Object instance, Object value) {
        setFieldInstance(false,clazz,name,instance,value);
    }
    
    public static void setFieldInstance(boolean isFinal, Class<?> clazz, String name, Object instance, Object value) {
        Field field = getField(clazz,name);
        if(isFinal) modifyFinalField(instance,field,value);
        else setFieldInstance(field,instance,value);
    }
    
    public static void setFieldInstance(@Nullable Field field, Object instance, Object value) {
        if(Objects.nonNull(field)) {
            if(!field.isAccessible()) field.setAccessible(true);
            try {
                field.set(instance,value);
            } catch(IllegalAccessException ex) {
                TILRef.logError("Failed to set value of field to {}",value,ex);
            }
        } else TILRef.logError("Cannot set value of null field!");
    }
    
    public static void setFieldModifiers(@Nullable Field field, int modifiers) {
        if(Objects.nonNull(field)) setFieldValue(field,getField(Field.class,"modifiers"),modifiers);
        else TILRef.logError("Cannot change the modifiers of null field");
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
