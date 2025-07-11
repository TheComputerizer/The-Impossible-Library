package mods.thecomputerizer.theimpossiblelibrary.api.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Objects;

import static org.burningwave.core.assembler.StaticComponentContainer.Classes;
import static org.burningwave.core.assembler.StaticComponentContainer.Constructors;
import static org.burningwave.core.assembler.StaticComponentContainer.Driver;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

/**
 * Burningwave abstraction layer and helper methods
 */
public class Hacks {
    
    static final boolean NAMED_ENV = CoreAPI.isNamedEnv();
    static final boolean SRG_ENV = CoreAPI.isSrgEnv();
    static final int JAVA_VERSION = CoreAPI.javaVersion();
    
    /**
     * Finds and instantiates the target class using the given args
     */
    @IndirectCallers
    public static <T> T construct(String targetClass, Object ... args) {
        return construct(findClass(targetClass),args);
    }
    
    /**
     * Finds the target class via the reference class and instantiates it using the given args
     */
    @IndirectCallers
    public static <T> T construct(Class<?> reference, String targetClass, Object ... args) {
        return construct(findClass(reference,targetClass),args);
    }
    
    /**
     * Instantiates the target class using the given args
     */
    public static <T> T construct(Class<?> target, Object ... args) {
        if(Objects.isNull(target)) {
            TILRef.logError("Tried to call construct on null target class! (args = {})",args);
            return null;
        }
        return Constructors.newInstanceOf(target,args);
    }
    
    /**
     * Finds and instantiates the target class using the given args
     * Constructing a class directly will bypass any package-private, private, or protected access restrictions.
     */
    @IndirectCallers
    public static <T> T constructDirect(String targetClass, Object ... args) {
        return constructDirect(findClass(targetClass),args);
    }
    
    /**
     * Finds the target class via the reference class and instantiates it using the given args
     * Constructing a class directly will bypass any package-private, private, or protected access restrictions.
     */
    @IndirectCallers
    public static <T> T constructDirect(Class<?> reference, String targetClass, Object ... args) {
        return constructDirect(findClass(reference,targetClass),args);
    }
    
    /**
     * Instantiates the target class using the given args
     * Constructing a class directly will bypass any package-private, private, or protected access restrictions.
     */
    public static <T> T constructDirect(Class<?> target, Object ... args) {
        if(Objects.isNull(target)) {
            TILRef.logError("Tried to call constructDirect on null target class! (args = {})",args);
            return null;
        }
        return Constructors.newInstanceOf(target,args);
    }
    
    public static ClassLoader contextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
    
    public static Class<?> defaultCaller() {
        return Classes.getClass();
    }
    
    /**
     * Finds and returns a Class object with the target name using the given ClassLoader and the given caller class.
     * The initialize flag determines whether the target class will be initialized.
     */
    public static @Nullable Class<?> findClass(String target, ClassLoader loader, Class<?> caller, boolean initialize) {
        if(Misc.anyNull(target,loader,caller)) {
            TILRef.logError("Cannot find target class {} on {} with caller {}!",target,loader,caller);
            return null;
        }
        try {
            return Driver.getClassByName(target,initialize,loader,caller);
        } catch(Throwable t) {
            TILRef.logError("Failed to find class {} on loader {} with caller {}! (initialize={})",target,loader,
                            caller,initialize,t);
        }
        return null;
    }
    
    /**
     * Finds and returns a Class object with the target name using the given caller class.
     * The initialize flag determines whether the target class will be initialized.
     */
    public static @Nullable Class<?> findClass(String target, Class<?> caller, boolean initialize) {
        return Objects.nonNull(caller) ? findClass(target,contextClassLoader(),caller,initialize) :
                findClass(target,initialize);
    }
    
    /**
     * Finds and returns a Class object with the target name using the given caller class.
     * Does not attempt to initialize the target class.
     */
    public static @Nullable Class<?> findClass(String target, Class<?> caller) {
        return findClass(target,caller,false);
    }
    
    /**
     * Finds and returns a Class object with the target name using the given ClassLoader and the given caller class.
     * Does not attempt to initialize the target class.
     */
    public static @Nullable Class<?> findClass(String target, ClassLoader loader, Class<?> caller) {
        return findClass(target,loader,caller,false);
    }
    
    /**
     * Finds and returns a Class object with the target name using the given ClassLoader.
     * The initialize flag determines whether the target class will be initialized.
     */
    public static @Nullable Class<?> findClass(String target, ClassLoader loader, boolean initialize) {
        return Objects.nonNull(loader) ? findClass(target,loader,defaultCaller(),initialize) :
                findClass(target,initialize);
    }
    
    /**
     * Finds and returns a Class object with the target name using the given ClassLoader.
     * Does not attempt to initialize the target class.
     */
    public static @Nullable Class<?> findClass(String target, ClassLoader loader) {
        return findClass(target,loader,false);
    }
    
    /**
     * Finds and returns a Class object with the target name using the given ClassLoader.
     * The initialize flag determines whether the target class will be initialized.
     */
    public static @Nullable Class<?> findClass(String target, boolean initialize) {
        return findClass(target,contextClassLoader(),defaultCaller(),initialize);
    }
    
    /**
     * Finds and returns a Class object with the target name.
     * Does not attempt to initialize the target class.
     */
    public static @Nullable Class<?> findClass(String target) {
        return findClass(target,false);
    }
    
    /**
     * Uses a reference class to find and return the target class in the same package as the reference class.
     * The ClassLoader of the reference class will be used to find the target class.
     */
    public static @Nullable Class<?> findClass(@Nullable Class<?> reference, String target) {
        if(Objects.isNull(reference)) return findClass(target);
        return findClass(reference.getPackage().getName()+"."+target,reference.getClassLoader());
    }
    
    /**
     * Find and return the target class in given package
     */
    public static @Nullable Class<?> findClass(@Nullable Package pkg, String target) {
        return Objects.nonNull(pkg) ? findClass(pkg.getName(),target) : findClass(target);
    }
    
    
    /**
     * Find and return the target class in given package name
     */
    public static @Nullable Class<?> findClass(@Nullable String pkg, String target) {
        return Objects.nonNull(pkg) ? findClass(pkg+"."+target) : findClass(target);
    }
    
    /**
     * Returns a non-static field instance of the given name on the given object.
     * The "named" input will be used in named environments for the field with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T getField(Object target, String named, String intermediary) {
        return getField(target,isNamedEnv() ? named : intermediary);
    }
    
    /**
     * Returns a non-static field instance of the given name on the given object.
     */
    public static <T> T getField(Object target, String field) {
        if(Objects.isNull(target)) {
            TILRef.logError("Tried to call getField on null target object! (field = {})",field);
            return null;
        }
        if(Objects.isNull(field)) {
            TILRef.logError("Tried to call getField with null field name! (target = {})",target);
            return null;
        }
        return Fields.get(target,field);
    }
    
    /**
     * Returns a non-static field instance of the given name on the given object.
     * Getting a field directly will bypass any package-private, private, or protected access restrictions.
     * The "named" input will be used in named environments for the field with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T getFieldDirect(Object target, String named, String intermediary) {
        return getFieldDirect(target,isNamedEnv() ? named : intermediary);
    }
    
    /**
     * Returns a non-static field instance of the given name on the given object.
     * Getting a field directly will bypass any package-private, private, or protected access restrictions.
     */
    @IndirectCallers
    public static <T> T getFieldDirect(Object target, String field) {
        if(Objects.isNull(target)) {
            TILRef.logError("Tried to call getFieldDirect on null target object! (field = {})",field);
            return null;
        }
        if(Objects.isNull(field)) {
            TILRef.logError("Tried to call getFieldDirect with null field name! (target = {})",target);
            return null;
        }
        return Fields.getDirect(target,field);
    }
    
    /**
     * Finds the target class and returns a static field instance of the given name in it.
     * The "named" input will be used in named environments for the field with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T getFieldStatic(String targetClass, String named, String intermediary) {
        return getFieldStatic(findClass(targetClass),named,intermediary);
    }
    
    /**
     * Returns a static field instance of the given name in the target class.
     * The "named" input will be used in named environments for the field with "intermediary" being used otherwise.
     */
    public static <T> T getFieldStatic(Class<?> target, String named, String intermediary) {
        return getFieldStatic(target,isNamedEnv() ? named : intermediary);
    }
    
    /**
     * Finds the target class and returns a static field instance of the given name in it.
     */
    @IndirectCallers
    public static <T> T getFieldStatic(String targetClass, String field) {
        return getFieldStatic(findClass(targetClass),field);
    }
    
    /**
     * Returns a static field instance of the given name in the target class.
     */
    public static <T> T getFieldStatic(Class<?> target, String field) {
        if(Objects.isNull(target)) {
            TILRef.logError("Tried to call getFieldStatic on null target class! (field = {})",field);
            return null;
        }
        if(Objects.isNull(field)) {
            TILRef.logError("Tried to call getFieldStatic with null field name! (target = {})",target);
            return null;
        }
        return Fields.getStatic(target,field);
    }
    
    /**
     * Finds the target class and returns a static field instance of the given name in it.
     * Getting a field directly will bypass any package-private, private, or protected access restrictions.
     * The "named" input will be used in named environments for the field with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T getFieldStaticDirect(String targetClass, String named, String intermediary) {
        return getFieldStaticDirect(findClass(targetClass),named,intermediary);
    }
    
    /**
     * Returns a static field instance of the given name in the target class.
     * Getting a field directly will bypass any package-private, private, or protected access restrictions.
     * The "named" input will be used in named environments for the field with "intermediary" being used otherwise.
     */
    public static <T> T getFieldStaticDirect(Class<?> target, String named, String intermediary) {
        return getFieldStaticDirect(target,isNamedEnv() ? named : intermediary);
    }
    
    /**
     * Finds the target class and returns a static field instance of the given name in it.
     * Getting a field directly will bypass any package-private, private, or protected access restrictions.
     */
    @IndirectCallers
    public static <T> T getFieldStaticDirect(String targetClass, String field) {
        return getFieldStaticDirect(findClass(targetClass),field);
    }
    
    /**
     * Returns a static field instance of the given name in the target class.
     * Getting a field directly will bypass any package-private, private, or protected access restrictions.
     */
    public static <T> T getFieldStaticDirect(Class<?> target, String field) {
        if(Objects.isNull(target)) {
            TILRef.logError("Tried to call getFieldStaticDirect on null target class! (field = {})",field);
            return null;
        }
        if(Objects.isNull(field)) {
            TILRef.logError("Tried to call getFieldStaticDirect with null field name! (target = {})",target);
            return null;
        }
        return Fields.getStaticDirect(target,field);
    }
    
    /**
     * Returns the current Java version as an integer.
     * This will return 17 by default if the Java versions fails to parse for some reason.
     */
    @IndirectCallers
    public static int getJavaVersion() {
        return JAVA_VERSION;
    }
    
    /**
     * Returns a record component with the given name if it exists in the given class.
     * Returns null if the call is invalid or the record does not exist.
     */
    private static Object getRecordComponent(Class<?> target, String field) {
        if(Objects.isNull(target)) {
            TILRef.logError("Tried to get record component of null class target! (field = {})",field);
            return null;
        }
        if(Objects.isNull(field)) {
            TILRef.logError("Tried to get record component for null field name! (target = {})",target);
            return null;
        }
        if(isJava8()) {
            TILRef.logError("Cannot get record component in Java 8 environment! Records were introduced in Java"+
                            " 14! ({}.{})",target.getName(),field);
            return null;
        }
        Object[] components = invoke(target,"getRecordComponents");
        if(Objects.isNull(components)) {
            TILRef.logError("No record components found in target {}!",target);
            return null;
        }
        for(Object component : components)
            if(field.equals(invoke(component,"getName"))) return component;
        TILRef.logError("No record components matching '{}' found in target {}!",field,target);
        return null;
    }
    
    /**
     * Returns the instance of a record field assuming the call is valid and the field exists.
     * Returns null if there is an issue retrieving the field instance.
     */
    public static <T> T getRecordField(Object target, String field) {
        if(Objects.isNull(target)) {
            TILRef.logError("Cannot get record field {} from null object target!",field);
            return null;
        }
        Class<?> targetClass = target.getClass();
        return getRecordFieldInstance(targetClass,target,field,getRecordComponent(targetClass,field));
    }
    
    /**
     * The default Field component for BurningWave utilizes Unsafe in the backend.
     * Unsafe does not support the retrieval of field offsets for record fields so we need to work around that.
     */
    @SuppressWarnings("unchecked")
    private static <T> T getRecordFieldInstance(Class<?> targetClass, @Nullable Object target, String name,
            @Nullable Object component) {
        if(Objects.isNull(component)) {
            TILRef.logError("Failed to get record component for target {}! (field = {})",targetClass,name);
            return null;
        }
        Field field;
        try {
            field = Classes.getDeclaredField(targetClass,f -> name.equals(f.getName()));
            if(Objects.isNull(field)) {
                TILRef.logError("Found null field {} for class {}",name,targetClass.getName());
                return null;
            }
            Driver.setAccessible(field,true);
            return (T)field.get(target);
        } catch(Throwable t) {
            TILRef.logError("Failed to find field {} in target {}!",name,targetClass,t);
        }
        return null;
    }
    
    /**
     * Returns the instance of a static record field assuming the call is valid and the field exists.
     * Returns null if there is an issue retrieving the field instance.
     */
    @IndirectCallers
    public static <T> T getRecordFieldStatic(Class<?> target, String field) {
        return getRecordFieldInstance(target,null,field,getRecordComponent(target,field));
    }
    
    /**
     * Invoke a non-static method of the given name on the given object with the given args.
     * The "named" input will be used in named environments for the method with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T invoke(Object target, String named, String intermediary, Object ... args) {
        return invoke(target,isNamedEnv() ? named : intermediary, args);
    }
    
    /**
     * Invoke a non-static method of the given name on the given object with the given args.
     */
    public static <T> T invoke(Object target, String method, Object ... args) {
        if(Objects.isNull(target)) {
            TILRef.logError("Tried to call invoke on null target object! (method = {} | args = {})",method,args);
            return null;
        }
        if(Objects.isNull(method)) {
            TILRef.logError("Tried to call invoke with null method name! (target = {} | args = {})",target,args);
            return null;
        }
        return Methods.invoke(target,method,args);
    }
    
    /**
     * Invoke a non-static method of the given name on the given object with the given args.
     * Invoking a method directly will bypass any package-private, private, or protected access restrictions.
     * The "named" input will be used in named environments for the method with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T invokeDirect(Object target, String named, String intermediary, Object ... args) {
        return invokeDirect(target, isNamedEnv() ? named : intermediary, args);
    }
    
    /**
     * Invoke a non-static method of the given name on the given object with the given args.
     * Invoking a method directly will bypass any package-private, private, or protected access restrictions.
     */
    public static <T> T invokeDirect(Object target, String method, Object ... args) {
        if(Objects.isNull(target)) {
            TILRef.logError("Tried to call invokeDirect on null target object! (method = {} | args = {})",
                            method,args);
            return null;
        }
        if(Objects.isNull(method)) {
            TILRef.logError("Tried to call invokeDirect with null method name! (target = {} | args = {})",
                            target,args);
            return null;
        }
        return Methods.invoke(target,method,args);
    }
    
    /**
     * Finds the target class and invokes a static method of the given name in it with the given args.
     * The "named" input will be used in named environments for the method with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T invokeStatic(String targetClass, String named, String intermediary, Object ... args) {
        return invokeStatic(findClass(targetClass),named,intermediary,args);
    }
    
    /**
     * Invoke a static method of the given name in the target class with the given args.
     * The "named" input will be used in named environments for the method with "intermediary" being used otherwise.
     */
    public static <T> T invokeStatic(Class<?> target, String named, String intermediary, Object ... args) {
        return invokeStatic(target,isNamedEnv() ? named : intermediary,args);
    }
    
    /**
     * Finds the target class and invokes a static method of the given name in it with the given args.
     */
    @IndirectCallers
    public static <T> T invokeStatic(String targetClass, String method, Object ... args) {
        return invokeStatic(findClass(targetClass),method,args);
    }
    
    /**
     * Invoke a static method of the given name in the target class with the given args.
     */
    public static <T> T invokeStatic(Class<?> target, String method, Object ... args) {
        if(Objects.isNull(target)) {
            TILRef.logError("Tried to call invokeStatic on null target object! (method = {} | args = {})",
                            method,args);
            return null;
        }
        if(Objects.isNull(method)) {
            TILRef.logError("Tried to call invokeStatic with null method name! (target = {} | args = {})",
                            target,args);
            return null;
        }
        return Methods.invokeStatic(target,method,args);
    }
    
    /**
     * Finds the target class and invokes a static method of the given name in it with the given args.
     * Invoking a method directly will bypass any package-private, private, or protected access restrictions.
     * The "named" input will be used in named environments for the method with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T invokeStaticDirect(String targetClass, String named, String intermediary, Object ... args) {
        return invokeStaticDirect(findClass(targetClass),named,intermediary,args);
    }
    
    /**
     * Invoke a static method of the given name in the target class with the given args.
     * Invoking a method directly will bypass any package-private, private, or protected access restrictions.
     * The "named" input will be used in named environments for the method with "intermediary" being used otherwise.
     */
    public static <T> T invokeStaticDirect(Class<?> target, String named, String intermediary, Object ... args) {
        return invokeStaticDirect(target,isNamedEnv() ? named : intermediary, args);
    }
    
    /**
     * Finds the target class and invokes a static method of the given name in it with the given args.
     * Invoking a method directly will bypass any package-private, private, or protected access restrictions.
     * The "named" input will be used in named environments for the method with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T invokeStaticDirect(String targetClass, String method, Object ... args) {
        return invokeStaticDirect(findClass(targetClass),method,args);
    }
    
    /**
     * Invoke a static method of the given name in the target class with the given args.
     * Invoking a method directly will bypass any package-private, private, or protected access restrictions.
     */
    public static <T> T invokeStaticDirect(Class<?> target, String method, Object ... args) {
        if(Objects.isNull(target)) {
            TILRef.logError("Tried to call invokeStaticDirect on null target object! (method = {} | args = {})",
                            method,args);
            return null;
        }
        if(Objects.isNull(method)) {
            TILRef.logError("Tried to call invokeStaticDirect with null method name! (target = {} | args = {})",
                            target,args);
            return null;
        }
        return Methods.invokeStaticDirect(target,method,args);
    }
    
    /**
     * Returns true if the Java version is 8
     * Java 8 is used in versions up to 1.16.5 except cleanroom for 1.12.2.
     */
    public static boolean isJava8() {
        return JAVA_VERSION==8;
    }
    
    /**
     * Returns true if the Java version is 17
     * Java 17 is used from 1.18.2 to 1.20.4
     */
    @IndirectCallers
    public static boolean isJava17() {
        return JAVA_VERSION==17;
    }
    
    /**
     * Returns true if the Java version is AT LEAST 21.
     * Java 21+ is used in 1.20.6+ as well as cleanroom for 1.12.2
     */
    @IndirectCallers
    public static boolean isJava21() {
        return JAVA_VERSION>=21;
    }
    
    /**
     * Returns true if this is a named environment (for reflection purposes).
     * Named environments include dev environments, Neoforge 1.20.4, and any version on any modloader past 1.20.4.
     */
    public static boolean isNamedEnv() {
        return NAMED_ENV;
    }
    
    /**
     * Returns true if this is a srg environment (for reflection purposes).
     * Srg environments include any non-named forge/neoforge environment.
     */
    @IndirectCallers
    public static boolean isSrgEnv() {
        return SRG_ENV;
    }
}