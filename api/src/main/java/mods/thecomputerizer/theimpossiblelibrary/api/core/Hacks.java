package mods.thecomputerizer.theimpossiblelibrary.api.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import static org.burningwave.core.assembler.StaticComponentContainer.Constructors;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

/**
 * Burningwave abstraction layer & helper methods
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
        return construct(ClassHelper.findClass(targetClass),args);
    }
    
    /**
     * Instantiates the target class using the given args
     */
    public static <T> T construct(Class<?> target, Object ... args) {
        return Constructors.newInstanceOf(target,args);
    }
    
    /**
     * Finds and instantiates the target class using the given args
     * Constructing a class directly will bypass any package-private, private, or protected access restrictions.
     */
    @IndirectCallers
    public static <T> T constructDirect(String targetClass, Object ... args) {
        return constructDirect(ClassHelper.findClass(targetClass),args);
    }
    
    /**
     * Instantiates the target class using the given args
     * Constructing a class directly will bypass any package-private, private, or protected access restrictions.
     */
    public static <T> T constructDirect(Class<?> target, Object ... args) {
        return Constructors.newInstanceOf(target,args);
    }
    
    /**
     * Returns a non-static field instance of the given name on the given object.
     * The "named" input will be used in named environments for the field with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T getField(Object target, String named, String intermediary) {
        return getField(target, isNamedEnv() ? named : intermediary);
    }
    
    /**
     * Returns a non-static field instance of the given name on the given object.
     */
    public static <T> T getField(Object target, String field) {
        return Fields.get(target,field);
    }
    
    /**
     * Returns a non-static field instance of the given name on the given object.
     * Getting a field directly will bypass any package-private, private, or protected access restrictions.
     * The "named" input will be used in named environments for the field with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T getFieldDirect(Object target, String named, String intermediary) {
        return Fields.getDirect(target, isNamedEnv() ? named : intermediary);
    }
    
    /**
     * Returns a non-static field instance of the given name on the given object.
     * Getting a field directly will bypass any package-private, private, or protected access restrictions.
     */
    @IndirectCallers
    public static <T> T getFieldDirect(Object target, String field) {
        return Fields.getDirect(target,field);
    }
    
    /**
     * Finds the target class and returns a static field instance of the given name in it.
     * The "named" input will be used in named environments for the field with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T getFieldStatic(String targetClass, String named, String intermediary) {
        return getFieldStatic(ClassHelper.findClass(targetClass),named,intermediary);
    }
    
    /**
     * Returns a static field instance of the given name in the target class.
     * The "named" input will be used in named environments for the field with "intermediary" being used otherwise.
     */
    public static <T> T getFieldStatic(Class<?> target, String named, String intermediary) {
        return getFieldStatic(target, isNamedEnv() ? named : intermediary);
    }
    
    /**
     * Finds the target class and returns a static field instance of the given name in it.
     */
    @IndirectCallers
    public static <T> T getFieldStatic(String targetClass, String field) {
        return getFieldStatic(ClassHelper.findClass(targetClass),field);
    }
    
    /**
     * Returns a static field instance of the given name in the target class.
     */
    public static <T> T getFieldStatic(Class<?> target, String field) {
        return Fields.getStatic(target,field);
    }
    
    /**
     * Finds the target class and returns a static field instance of the given name in it.
     * Getting a field directly will bypass any package-private, private, or protected access restrictions.
     * The "named" input will be used in named environments for the field with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T getFieldStaticDirect(String targetClass, String named, String intermediary) {
        return getFieldStaticDirect(ClassHelper.findClass(targetClass),named,intermediary);
    }
    
    /**
     * Returns a static field instance of the given name in the target class.
     * Getting a field directly will bypass any package-private, private, or protected access restrictions.
     * The "named" input will be used in named environments for the field with "intermediary" being used otherwise.
     */
    public static <T> T getFieldStaticDirect(Class<?> target, String named, String intermediary) {
        return getFieldStaticDirect(target, isNamedEnv() ? named : intermediary);
    }
    
    /**
     * Finds the target class and returns a static field instance of the given name in it.
     * Getting a field directly will bypass any package-private, private, or protected access restrictions.
     */
    @IndirectCallers
    public static <T> T getFieldStaticDirect(String targetClass, String field) {
        return getFieldStaticDirect(ClassHelper.findClass(targetClass),field);
    }
    
    /**
     * Returns a static field instance of the given name in the target class.
     * Getting a field directly will bypass any package-private, private, or protected access restrictions.
     */
    public static <T> T getFieldStaticDirect(Class<?> target, String field) {
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
     * Invoke a non-static method of the given name on the given object with the given args.
     * The "named" input will be used in named environments for the method with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T invoke(Object target, String named, String intermediary, Object ... args) {
        return invoke(target, isNamedEnv() ? named : intermediary, args);
    }
    
    /**
     * Invoke a non-static method of the given name on the given object with the given args.
     */
    public static <T> T invoke(Object target, String method, Object ... args) {
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
        return Methods.invoke(target,method,args);
    }
    
    /**
     * Finds the target class and invokes a static method of the given name in it with the given args.
     * The "named" input will be used in named environments for the method with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T invokeStatic(String targetClass, String named, String intermediary, Object ... args) {
        return invokeStatic(ClassHelper.findClass(targetClass),named,intermediary,args);
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
        return invokeStatic(ClassHelper.findClass(targetClass),method,args);
    }
    
    /**
     * Invoke a static method of the given name in the target class with the given args.
     */
    public static <T> T invokeStatic(Class<?> target, String method, Object ... args) {
        return Methods.invokeStatic(target,method,args);
    }
    
    /**
     * Finds the target class and invokes a static method of the given name in it with the given args.
     * Invoking a method directly will bypass any package-private, private, or protected access restrictions.
     * The "named" input will be used in named environments for the method with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T invokeStaticDirect(String targetClass, String named, String intermediary, Object ... args) {
        return invokeStaticDirect(ClassHelper.findClass(targetClass),named,intermediary,args);
    }
    
    /**
     * Invoke a static method of the given name in the target class with the given args.
     * Invoking a method directly will bypass any package-private, private, or protected access restrictions.
     * The "named" input will be used in named environments for the method with "intermediary" being used otherwise.
     */
    public static <T> T invokeStaticDirect(Class<?> target, String named, String intermediary, Object ... args) {
        return invokeStaticDirect(target, isNamedEnv() ? named : intermediary, args);
    }
    
    /**
     * Finds the target class and invokes a static method of the given name in it with the given args.
     * Invoking a method directly will bypass any package-private, private, or protected access restrictions.
     * The "named" input will be used in named environments for the method with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T invokeStaticDirect(String targetClass, String method, Object ... args) {
        return invokeStaticDirect(ClassHelper.findClass(targetClass),method,args);
    }
    
    /**
     * Invoke a static method of the given name in the target class with the given args.
     * Invoking a method directly will bypass any package-private, private, or protected access restrictions.
     */
    public static <T> T invokeStaticDirect(Class<?> target, String method, Object ... args) {
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