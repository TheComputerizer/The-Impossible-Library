package mods.thecomputerizer.theimpossiblelibrary.api.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import org.apache.logging.log4j.Logger;
import org.burningwave.core.assembler.StaticComponentContainer.Configuration.Default;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static java.lang.System.err;
import static org.burningwave.core.assembler.StaticComponentContainer.ClassLoaders;
import static org.burningwave.core.assembler.StaticComponentContainer.Classes;
import static org.burningwave.core.assembler.StaticComponentContainer.Constructors;
import static org.burningwave.core.assembler.StaticComponentContainer.Driver;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

/**
 * Burningwave abstraction layer and helper methods
 */
public class Hacks {
    
    static final String SELF_NAME = "mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks";
    static final Logger LOGGER = TILRef.createLogger("TIL Hacks (BurningWave)");
    static final int JAVA_VERSION = CoreAPI.javaVersion();
    
    /**
     * This class might be initialized before the CoreAPI instance so we need to defer any environment-dependent checks
     */
    static boolean initializedEnvironment;
    static boolean namedEnv;
    static boolean srgEnv;
    
    static boolean burningWaveInit;

    /**
     * Returns true if the value was added.
     * Assumes the collection field is modifiable.
     */
    @IndirectCallers
    public static <V> boolean addToCollectionField(String field, V value,
            Function<String,Collection<V>> collectionGetter) {
        return addToCollectionField(field,value,collectionGetter,null);
    }
    
    /**
     * Returns true if the value was added.
     */
    public static <V> boolean addToCollectionField(String field, V value,
            Function<String,Collection<V>> collectionGetter,
            @Nullable BiConsumer<String,Collection<V>> afterAdd) {
        Collection<V> collection = collectionGetter.apply(field);
        boolean added = collection.add(value);
        if(Objects.nonNull(afterAdd)) afterAdd.accept(field,collection);
        return added;
    }
    
    /**
     * Returns true if any of the values were added.
     * Assumes the collection field is modifiable.
     */
    @IndirectCallers
    public static <V> boolean addToCollectionField(String field, Collection<V> values,
            Function<String,Collection<V>> collectionGetter) {
        return addToCollectionField(field,values,collectionGetter,null);
    }
    
    /**
     * Returns true if the value was added.
     */
    public static <V> boolean addToCollectionField(String field, Collection<V> values,
            Function<String,Collection<V>> collectionGetter,
            @Nullable BiConsumer<String,Collection<V>> afterAdd) {
        Collection<V> collection = collectionGetter.apply(field);
        boolean added = collection.addAll(values);
        if(Objects.nonNull(afterAdd)) afterAdd.accept(field,collection);
        return added;
    }
    
    /**
     * Returns the previous value associated with the key or null if the map did not contain the key.
     * Assumes the map field is modifiable.
     */
    @IndirectCallers
    public static <K,V> V addToMapField(String field, K key, V value, Function<String,Map<K,V>> mapGetter) {
        return addToMapField(field,key,value,mapGetter,null);
    }
    
    /**
     * Returns the previous value associated with the key or null if the map did not contain the key.
     */
    public static <K,V> V addToMapField(String field, K key, V value, Function<String,Map<K,V>> mapGetter,
            @Nullable BiConsumer<String,Map<K,V>> afterAdd) {
        Map<K,V> map = mapGetter.apply(field);
        V replacedValue = map.put(key,value);
        if(Objects.nonNull(afterAdd)) afterAdd.accept(field,map);
        return replacedValue;
    }
    
    /**
     * Set some default BurningWave properties
     */
    static Map<?,?> burningWaveProperties() {
        Map<Object,Object> properties = new HashMap<>();
        //Hide the large BurningWave banner that gets logged during intialization (which could happen multiple times)
        properties.put("banner.hide","true");
        //Tell BurningWave to use the native driver for the greatest reach instead of the default driver
        properties.put("jvm.driver.type","org.burningwave.jvm.NativeDriver");
        //Disable some log spam that happens during BurningWave initialization (which could happen multiple times)
        properties.put("managed-logger.repository.enabled","false");
        //Increase the priority of these properties to ensure they are checked first
        properties.put("priority-of-this-configuration","1000");
        //Disable the resource releaser to prevent a shutdown hook crash
        properties.put("resource-releaser.enabled","false");
        return properties;
    }
    
    public static <T> T callOnOtherClassLoader(ClassLoader loader, String method, Object ... args) {
        boolean staticCall = method.contains("static") || method.contains("Static");
        Class<?>[] argClasses = new Class<?>[]{(staticCall ? Class.class : Object.class),String.class,Object[].class};
        if(method.contains("field") || method.contains("Field")) argClasses[2] = Object.class;
        return callOnOtherClassLoader(loader,method,argClasses,args);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T callOnOtherClassLoader(ClassLoader loader, String method, Class<?>[] argClasses,
            Object ... args) {
        try {
            Class<?> c = Class.forName(SELF_NAME,false,loader);
            return (T)c.getDeclaredMethod(method,argClasses).invoke(null,args);
        } catch(Throwable t) {
            LOGGER.error("Failed to call {}#{} on ClassLoader {} with args {}",SELF_NAME,method,loader,args);
            t.printStackTrace(err); //Avoid the logger loading stacktrace classes
        }
        return null;
    }
    
    /**
     * Check if BurningWave has been initialized and set the default properties if not
     */
    public static void checkBurningWaveInit() {
        if(!burningWaveInit) {
            try {
                Default.add(burningWaveProperties());
            } catch(Throwable t) {
                LOGGER.warn("Tried to set default BurningWave properties twice");
            }
            burningWaveInit = true;
        }
    }
    
    private static void checkEnvironmentInit() {
        if(!initializedEnvironment) {
            try {
                namedEnv = CoreAPI.isNamedEnv();
                srgEnv = CoreAPI.isSrgEnv();
                initializedEnvironment = true; //Only mark initialized if nothing is thrown
            } catch(Throwable t) {
                LOGGER.fatal("Failed to initialize environment-dependent checks!",t);
            }
        }
    }
    
    /**
     * Finds and instantiates the target class using the given args
     */
    public static <T> T construct(String targetClass, Object ... args) {
        return construct(findClass(targetClass),args);
    }
    
    /**
     * Instantiates the target class using the given args
     */
    public static <T> T construct(Class<?> target, Object ... args) {
        if(Objects.isNull(target)) {
            LOGGER.error("Tried to call construct on null target class! (args = {})",args);
            return null;
        }
        try {
            return Constructors.newInstanceOf(target,args);
        } catch(Throwable t) {
            LOGGER.error("Failed to contruct {} with args {}",target,args);
            throw t;
        }
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
            LOGGER.error("Tried to call constructDirect on null target class! (args = {})",args);
            return null;
        }
        return Constructors.newInstanceOf(target,args);
    }
    
    /**
     * Finds the target class via the reference class and instantiates it using the given args
     */
    @IndirectCallers
    public static <T> T constructFromReference(Class<?> reference, String targetClass, Object ... args) {
        return construct(findClass(reference,targetClass),args);
    }
    
    /**
     * Finds and instantiates the target class on the target class loader using the given args
     */
    @IndirectCallers
    public static <T> T constructWithLoader(String target, ClassLoader loader, Object ... args) {
        return construct(findClass(target,loader),args);
    }
    
    public static ClassLoader contextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
    
    public static Class<?> defaultCaller() {
        return Classes.getClass();
    }
    
    /**
     * Defines and resolves a class from byteCode
     */
    public static Class<?> defineClass(ClassLoader loader, String name, @Nullable ByteBuffer buffer) {
        if(java.util.Objects.isNull(buffer))
            throw new NullPointerException("Tried to define class with null ByteBuffer: "+name);
        try {
            checkBurningWaveInit();
            return ClassLoaders.loadOrDefineByByteCode(buffer,loader);
        } catch(Throwable t) {
            TILRef.logError("Failed to define class {} on {}",name,loader,t);
        }
        return null;
    }
    
    /**
     * Finds and returns a Class object with the target name using the given ClassLoader and the given caller class.
     * The initialize flag determines whether the target class will be initialized.
     */
    public static @Nullable Class<?> findClass(String target, ClassLoader loader, Class<?> caller, boolean initialize) {
        if(Misc.anyNull(target,loader,caller)) {
            LOGGER.error("Cannot find target class {} on {} with caller {}!",target,loader,caller);
            return null;
        }
        try {
            return Driver.getClassByName(target,initialize,loader,caller);
        } catch(Throwable t) {
            LOGGER.error("Failed to find class {} on loader {} with caller {}! (initialize={})",target,loader,
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
            LOGGER.error("Tried to call getField on null target object! (field = {})",field);
            return null;
        }
        if(Objects.isNull(field)) {
            LOGGER.error("Tried to call getField with null field name! (target = {})",target);
            return null;
        }
        return Fields.get(target,field);
    }
    
    @IndirectCallers
    public static <E,C extends Collection<E>> C getFieldCollection(String field, Function<String,C> getter) {
        return getFieldCollection(field,getter,null);
    }
    
    public static <E,C extends Collection<E>> C getFieldCollection(String field,
            Function<String,C> getter, Function<C,C> ifNotNull) {
        C collection = getter.apply(field);
        return Objects.isNull(ifNotNull) ? collection :
                (Objects.nonNull(collection) ? ifNotNull.apply(collection) : null);
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
            LOGGER.error("Tried to call getFieldDirect on null target object! (field = {})",field);
            return null;
        }
        if(Objects.isNull(field)) {
            LOGGER.error("Tried to call getFieldDirect with null field name! (target = {})",target);
            return null;
        }
        return Fields.getDirect(target,field);
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
            LOGGER.error("Tried to call getFieldStatic on null target class! (field = {})",field);
            return null;
        }
        if(Objects.isNull(field)) {
            LOGGER.error("Tried to call getFieldStatic with null field name! (target = {})",target);
            return null;
        }
        return Fields.getStatic(target,field);
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
            LOGGER.error("Tried to call getFieldStaticDirect on null target class! (field = {})",field);
            return null;
        }
        if(Objects.isNull(field)) {
            LOGGER.error("Tried to call getFieldStaticDirect with null field name! (target = {})",target);
            return null;
        }
        return Fields.getStaticDirect(target,field);
    }
    
    /**
     * Finds the target class and returns a static field instance of the given name in it.
     * Getting a field directly will bypass any package-private, private, or protected access restrictions.
     * The "named" input will be used in named environments for the field with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T getFieldStaticDirectNamed(String targetClass, String named, String intermediary) {
        return getFieldStaticDirectNamed(findClass(targetClass),named,intermediary);
    }
    
    /**
     * Returns a static field instance of the given name in the target class.
     * Getting a field directly will bypass any package-private, private, or protected access restrictions.
     * The "named" input will be used in named environments for the field with "intermediary" being used otherwise.
     */
    public static <T> T getFieldStaticDirectNamed(Class<?> target, String named, String intermediary) {
        return getFieldStaticDirect(target,isNamedEnv() ? named : intermediary);
    }
    
    /**
     * Finds the target class and returns a static field instance of the given name in it.
     * The "named" input will be used in named environments for the field with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T getFieldStaticNamed(String targetClass, String named, String intermediary) {
        return getFieldStaticNamed(findClass(targetClass),named,intermediary);
    }
    
    /**
     * Returns a static field instance of the given name in the target class.
     * The "named" input will be used in named environments for the field with "intermediary" being used otherwise.
     */
    public static <T> T getFieldStaticNamed(Class<?> target, String named, String intermediary) {
        return getFieldStatic(target,isNamedEnv() ? named : intermediary);
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
            LOGGER.error("Tried to get record component of null class target! (field = {})",field);
            return null;
        }
        if(Objects.isNull(field)) {
            LOGGER.error("Tried to get record component for null field name! (target = {})",target);
            return null;
        }
        if(isJava8()) {
            LOGGER.error("Cannot get record component in Java 8 environment! Records were introduced in Java"+
                            " 14! ({}.{})",target.getName(),field);
            return null;
        }
        Object[] components = invoke(target,"getRecordComponents");
        if(Objects.isNull(components)) {
            LOGGER.error("No record components found in target {}!",target);
            return null;
        }
        for(Object component : components)
            if(field.equals(invoke(component,"getName"))) return component;
        LOGGER.error("No record components matching '{}' found in target {}!",field,target);
        return null;
    }
    
    /**
     * Returns the instance of a record field assuming the call is valid and the field exists.
     * Returns null if there is an issue retrieving the field instance.
     */
    public static <T> T getRecordField(Object target, String field) {
        if(Objects.isNull(target)) {
            LOGGER.error("Cannot get record field {} from null object target!",field);
            return null;
        }
        Class<?> targetClass = target.getClass();
        return getRecordFieldInstance(getRecordComponent(targetClass,field),target,field);
    }
    
    /**
     * The default Field component for BurningWave utilizes Unsafe in the backend.
     * Unsafe does not support the retrieval of field offsets for record fields so we need to work around that.
     */
    private static <T> T getRecordFieldInstance(@Nullable Object component, @Nullable Object target,
            String name) {
        if(Objects.isNull(component)) {
            LOGGER.error("Failed to get record field instance! (field = {})",name);
            return null;
        }
        return invokeMethodObj(target,invoke(component,"getAccessor"));
    }
    
    /**
     * Returns the instance of a static record field assuming the call is valid and the field exists.
     * Returns null if there is an issue retrieving the field instance.
     */
    @IndirectCallers
    public static <T> T getRecordFieldStatic(Class<?> target, String field) {
        return getRecordFieldInstance(getRecordComponent(target,field),null,field);
    }
    
    /**
     * Invoke a non-static method of the given name on the given object with the given args.
     */
    public static <T> T invoke(Object target, String method, Object ... args) {
        if(Objects.isNull(target)) {
            LOGGER.error("Tried to call invoke on null target object! (method = {} | args = {})",method,args);
            return null;
        }
        if(Objects.isNull(method)) {
            LOGGER.error("Tried to call invoke with null method name! (target = {} | args = {})",target,args);
            return null;
        }
        return Methods.invoke(target,method,args);
    }
    
    /**
     * Invoke a non-static method of the given name on the given object with the given args.
     * Invoking a method directly will bypass any package-private, private, or protected access restrictions.
     */
    public static <T> T invokeDirect(Object target, String method, Object ... args) {
        if(Objects.isNull(target)) {
            LOGGER.error("Tried to call invokeDirect on null target object! (method = {} | args = {})",
                            method,args);
            return null;
        }
        if(Objects.isNull(method)) {
            LOGGER.error("Tried to call invokeDirect with null method name! (target = {} | args = {})",
                            target,args);
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
    public static <T> T invokeDirectNamed(Object target, String named, String intermediary, Object ... args) {
        return invokeDirect(target, isNamedEnv() ? named : intermediary, args);
    }
    
    public static <T> T invokeMethodObj(@Nullable Object target, Method method, Object ... args) {
        if(Objects.isNull(method)) {
            LOGGER.error("Cannot invoke null method object! (args={})",(Object)args);
        }
        return Methods.invoke(target,method,args);
    }
    
    /**
     * Invoke a non-static method of the given name on the given object with the given args.
     * The "named" input will be used in named environments for the method with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T invokeNamed(Object target, String named, String intermediary, Object ... args) {
        return invoke(target,isNamedEnv() ? named : intermediary, args);
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
            LOGGER.error("Tried to call invokeStatic on null target class! (method = {} | args = {})",
                            method,args);
            return null;
        }
        if(Objects.isNull(method)) {
            LOGGER.error("Tried to call invokeStatic with null method name! (target = {} | args = {})",
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
    public static <T> T invokeStaticDirect(String targetClass, String method, Object ... args) {
        return invokeStaticDirect(findClass(targetClass),method,args);
    }
    
    /**
     * Invoke a static method of the given name in the target class with the given args.
     * Invoking a method directly will bypass any package-private, private, or protected access restrictions.
     */
    public static <T> T invokeStaticDirect(Class<?> target, String method, Object ... args) {
        if(Objects.isNull(target)) {
            LOGGER.error("Tried to call invokeStaticDirect on null target class! (method = {} | args = {})",
                            method,args);
            return null;
        }
        if(Objects.isNull(method)) {
            LOGGER.error("Tried to call invokeStaticDirect with null method name! (target = {} | args = {})",
                            target,args);
            return null;
        }
        return Methods.invokeStaticDirect(target,method,args);
    }
    
    /**
     * Finds the target class and invokes a static method of the given name in it with the given args.
     * Invoking a method directly will bypass any package-private, private, or protected access restrictions.
     * The "named" input will be used in named environments for the method with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T invokeStaticDirectNamed(String targetClass, String named, String intermediary, Object ... args) {
        return invokeStaticDirect(findClass(targetClass),named,intermediary,args);
    }
    
    /**
     * Invoke a static method of the given name in the target class with the given args.
     * Invoking a method directly will bypass any package-private, private, or protected access restrictions.
     * The "named" input will be used in named environments for the method with "intermediary" being used otherwise.
     */
    @SuppressWarnings("UnusedReturnValue")
    public static <T> T invokeStaticDirectNamed(Class<?> target, String named, String intermediary, Object ... args) {
        return invokeStaticDirect(target,isNamedEnv() ? named : intermediary, args);
    }
    
    @IndirectCallers
    public static <T> T invokeStaticMethodObj(Method method, Object ... args) {
        return invokeMethodObj(null,method,args);
    }
    
    /**
     * Finds the target class and invokes a static method of the given name in it with the given args.
     * The "named" input will be used in named environments for the method with "intermediary" being used otherwise.
     */
    @IndirectCallers
    public static <T> T invokeStaticNamed(String targetClass, String named, String intermediary, Object ... args) {
        return invokeStatic(findClass(targetClass),named,intermediary,args);
    }
    
    /**
     * Invoke a static method of the given name in the target class with the given args.
     * The "named" input will be used in named environments for the method with "intermediary" being used otherwise.
     */
    public static <T> T invokeStaticNamed(Class<?> target, String named, String intermediary, Object ... args) {
        return invokeStatic(target,isNamedEnv() ? named : intermediary,args);
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
        checkEnvironmentInit();
        return namedEnv;
    }
    
    /**
     * Returns true if this is a srg environment (for reflection purposes).
     * Srg environments include any non-named forge/neoforge environment.
     */
    @IndirectCallers
    public static boolean isSrgEnv() {
        checkEnvironmentInit();
        return srgEnv;
    }
    
    @SuppressWarnings("UnusedReturnValue")
    public static <T> Class<T> loadOrDefineClass(Class<T> c, ClassLoader loader) {
        try {
            return ClassLoaders.loadOrDefine(c,loader);
        } catch(Exception ex) {
            LOGGER.fatal("Failed to load or define {} on loader {}",c,loader,ex);
        }
        return c;
    }
    
    /**
     * Returns true if the value was removed
     */
    @IndirectCallers
    public static <V> boolean removeCollectionFieldValue(String field, V value,
            Function<String,Collection<V>> collectionGetter) {
        return removeCollectionFieldValue(field,value,collectionGetter,null);
    }
    
    /**
     * Returns true if the value was removed
     */
    public static <V> boolean removeCollectionFieldValue(String field, V value,
            Function<String,Collection<V>> collectionGetter,
            @Nullable BiConsumer<String,Collection<V>> afterRemove) {
        Collection<V> collection = collectionGetter.apply(field);
        boolean removed = collection.remove(value);
        if(Objects.nonNull(afterRemove)) afterRemove.accept(field,collection);
        return removed;
    }
    
    public static void removeEnvironmentProperty(String property) {
        removeEnvironmentProperty(property,true);
    }
    
    public static void removeEnvironmentProperty(String property, boolean removeFromAll) {
        LOGGER.debug("Attempting to remove {} property",property);
        final String all = removeFromAll ? "all environment property maps" : "the default environment property map";
        final String cName = "java.lang.ProcessEnvironment";
        final Class<?> c = findClass(cName);
        if(Objects.isNull(c)) {
            LOGGER.error("Failed to find class {}! Cannot remove environment property {}",cName,property);
            return;
        }
        removeMapFieldKey("theEnvironment",property,s -> getFieldStaticDirect(c,s));
        if(removeFromAll) {
            removeMapFieldKey("theCaseInsensitiveEnvironment",property,s -> getFieldStaticDirect(c,s));
            removeMapFieldKey("theUnmodifiableEnvironment",property,s -> {
                Map<String,String> map = getFieldStaticDirect(c,s);
                return new HashMap<>(Objects.nonNull(map) ? map : Collections.emptyMap());
            },(s,map) -> setFieldStaticDirect(c,s,Collections.unmodifiableMap(map)));
        }
        LOGGER.info("Removed property {} from {}",property,all);
    }
    
    /**
     * Assumes the map field is modifiable
     */
    @SuppressWarnings("UnusedReturnValue")
    public static <K,V> V removeMapFieldKey(String field, K key, Function<String,Map<K,V>> mapGetter) {
        return removeMapFieldKey(field,key,mapGetter,null);
    }
    
    /**
     * Returns null if the map did not contain the key
     */
    public static <K,V> V removeMapFieldKey(String field, K key, Function<String,Map<K,V>> mapGetter,
            @Nullable BiConsumer<String,Map<K,V>> afterRemove) {
        Map<K,V> map = mapGetter.apply(field);
        V removedValue = map.remove(key);
        if(Objects.nonNull(afterRemove)) afterRemove.accept(field,map);
        return removedValue;
    }
    
    @IndirectCallers
    public static void setField(Object target, String field, Object value) {
        if(Objects.isNull(target)) {
            LOGGER.error("Tried to call setField on null target object! (field = {})",field);
            return;
        }
        if(Objects.isNull(field)) {
            LOGGER.error("Tried to call setField with null field name! (target = {})",target);
            return;
        }
        Fields.set(target,field,value);
    }
    
    @IndirectCallers
    public static void setFieldDirect(Object target, String field, Object value) {
        if(Objects.isNull(target)) {
            LOGGER.error("Tried to call setFieldDirect on null target object! (field = {})",field);
            return;
        }
        if(Objects.isNull(field)) {
            LOGGER.error("Tried to call setFieldDirect with null field name! (target = {})",target);
            return;
        }
        Fields.setDirect(target,field,value);
    }
    
    @IndirectCallers
    public static void setFieldStatic(String targetName, String field, Object value) {
        setFieldStatic(findClass(targetName),field,value);
    }
    
    public static void setFieldStatic(Class<?> target, String field, Object value) {
        if(Objects.isNull(target)) {
            LOGGER.error("Tried to call setFieldStatic on null target class! (field = {})",field);
            return;
        }
        if(Objects.isNull(field)) {
            LOGGER.error("Tried to call setFieldStatic with null field name! (target = {})",target);
            return;
        }
        Fields.setStatic(target,field,value);
    }
    
    @IndirectCallers
    public static void setFieldStaticDirect(String targetName, String field, Object value) {
        setFieldStaticDirect(findClass(targetName),field,value);
    }
    
    public static void setFieldStaticDirect(Class<?> target, String field, Object value) {
        if(Objects.isNull(target)) {
            LOGGER.error("Tried to call setFieldStaticDirect on null target class! (field = {})",field);
            return;
        }
        if(Objects.isNull(field)) {
            LOGGER.error("Tried to call setFieldStaticDirect with null field name! (target = {})",target);
            return;
        }
        Fields.setStaticDirect(target,field,value);
    }
}