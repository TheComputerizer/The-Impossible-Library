package mods.thecomputerizer.theimpossiblelibrary.api.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import org.apache.logging.log4j.Logger;
import org.burningwave.core.assembler.StaticComponentContainer.Configuration.Default;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static java.lang.System.err;
import static org.burningwave.core.assembler.StaticComponentContainer.ClassLoaders;
import static org.burningwave.core.assembler.StaticComponentContainer.Classes;
import static org.burningwave.core.assembler.StaticComponentContainer.Constructors;
import static org.burningwave.core.assembler.StaticComponentContainer.Driver;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;
import static org.burningwave.core.assembler.StaticComponentContainer.Streams;

/**
 * Burningwave abstraction layer and helper methods
 */
public class Hacks {
    
    static final String SELF_NAME = "mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks";
    static final Logger LOGGER = TILRef.createLogger("TIL Hacks (BurningWave)");
    
    static boolean burningWaveInit;
    
    /**
     * This class might be initialized before the CoreAPI instance so we need to defer any environment-dependent checks
     */
    static boolean initializedEnvironment;
    static boolean namedEnv;
    static boolean srgEnv;

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
        //Tell BurningWave to use the native (or hybrid) driver for the greatest reach instead of the default driver
        final String driverName = (JVMHelper.hasNativeAccess() ? "Native" : "Hybrid")+"Driver";
        properties.put("jvm.driver.type","org.burningwave.jvm."+driverName);
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
    
    /**
     * Ensuring BurningWave has been initialized before calling a Hacks method
     */
    @IndirectCallers
    public static <T> T checkBurningWaveInitAndCall(String method, Object ... args) {
        checkBurningWaveInit();
        return invokeStatic(Hacks.class,method,args);
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
    public static <T> T construct(Class<T> target, Object ... args) {
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
     * Instantiates the target generic class using the given args
     */
    public static <T> T constructAndCast(Class<?> target, Object ... args) {
        return GenericUtils.cast(construct(target,args));
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
    public static <T> Class<T> defineClass(ClassLoader loader, String name, @Nullable ByteBuffer buffer) {
        if(java.util.Objects.isNull(buffer))
            throw new NullPointerException("Tried to define class with null ByteBuffer: "+name);
        try {
            checkBurningWaveInit();
            return GenericUtils.cast(ClassLoaders.loadOrDefineByByteCode(buffer,loader));
        } catch(Throwable t) {
            TILRef.logError("Failed to define class {} on {}",name,loader,t);
        }
        return null;
    }
    
    /**
     * Finds and returns a Class object with the target name using the given ClassLoader and the given caller class.
     * The initialize flag determines whether the target class will be initialized.
     */
    public static <T> @Nullable Class<T> findClass(String target, ClassLoader loader, Class<?> caller,
            boolean initialize) {
        if(Misc.anyNull(target,loader,caller)) {
            LOGGER.error("Cannot find target class {} on {} with caller {}!",target,loader,caller);
            return null;
        }
        try {
            return GenericUtils.cast(Driver.getClassByName(target,initialize,loader,caller));
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
    public static <T> @Nullable Class<T> findClass(String target, Class<?> caller, boolean initialize) {
        return Objects.nonNull(caller) ? findClass(target,contextClassLoader(),caller,initialize) :
                findClass(target,initialize);
    }
    
    /**
     * Finds and returns a Class object with the target name using the given caller class.
     * Does not attempt to initialize the target class.
     */
    public static <T> @Nullable Class<T> findClass(String target, Class<?> caller) {
        return findClass(target,caller,false);
    }
    
    /**
     * Finds and returns a Class object with the target name using the given ClassLoader and the given caller class.
     * Does not attempt to initialize the target class.
     */
    public static <T> @Nullable Class<T> findClass(String target, ClassLoader loader, Class<?> caller) {
        return findClass(target,loader,caller,false);
    }
    
    /**
     * Finds and returns a Class object with the target name using the given ClassLoader.
     * The initialize flag determines whether the target class will be initialized.
     */
    public static <T> @Nullable Class<T> findClass(String target, ClassLoader loader, boolean initialize) {
        return Objects.nonNull(loader) ? findClass(target,loader,defaultCaller(),initialize) :
                findClass(target,initialize);
    }
    
    /**
     * Finds and returns a Class object with the target name using the given ClassLoader.
     * Does not attempt to initialize the target class.
     */
    public static <T> @Nullable Class<T> findClass(String target, ClassLoader loader) {
        return findClass(target,loader,false);
    }
    
    /**
     * Finds and returns a Class object with the target name using the given ClassLoader.
     * The initialize flag determines whether the target class will be initialized.
     */
    public static <T> @Nullable Class<T> findClass(String target, boolean initialize) {
        return findClass(target,contextClassLoader(),defaultCaller(),initialize);
    }
    
    /**
     * Finds and returns a Class object with the target name.
     * Does not attempt to initialize the target class.
     */
    public static <T> @Nullable Class<T> findClass(String target) {
        return findClass(target,false);
    }
    
    /**
     * Uses a reference class to find and return the target class in the same package as the reference class.
     * The ClassLoader of the reference class will be used to find the target class.
     */
    public static <T> @Nullable Class<T> findClass(@Nullable Class<?> reference, String target) {
        if(Objects.isNull(reference)) return findClass(target);
        return findClass(reference.getPackage().getName()+"."+target,reference.getClassLoader());
    }
    
    /**
     * Find and return the target class in given package
     */
    public static <T> @Nullable Class<T> findClass(@Nullable Package pkg, String target) {
        return Objects.nonNull(pkg) ? findClass(pkg.getName(),target) : findClass(target);
    }
    
    
    /**
     * Find and return the target class in given package name
     */
    public static <T> @Nullable Class<T> findClass(@Nullable String pkg, String target) {
        return Objects.nonNull(pkg) ? findClass(pkg+"."+target) : findClass(target);
    }
    
    public static Class<?> findClassInHeirarchy(String name, ClassLoader loader) {
        return findClassInHeirarchy(name,loader,false);
    }
    
    public static Class<?> findClassInHeirarchy(String name, ClassLoader loader, boolean initialize) {
        Class<?> foundClass = null;
        ClassLoader searchIn = loader;
        while(Objects.nonNull(searchIn)) {
            try {
                foundClass = Driver.getClassByName(name,initialize,loader,Classes.getClass());
            } catch(Throwable t) {
                LOGGER.debug("Class not found in ClassLoader {} (name = {})",searchIn,name);
            }
            if(Objects.nonNull(foundClass)) break;
            searchIn = ClassLoaders.getParent(searchIn);
        }
        if(Objects.isNull(foundClass)) {
            LOGGER.error("Class {} not found in ClassLoader heirarchy for {}",name,loader);
            return null;
        }
        return foundClass;
    }
    
    public static @Nullable ByteBuffer getByteCode(Class<?> clazz) {
        return Classes.getByteCode(clazz);
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
    public static <E> Collection<E> getFieldCollection(Object instance, String getter, String field) {
        Collection<E> collection = invokeStatic(Hacks.class,getter,instance,field);
        return Objects.nonNull(collection) ? collection : Collections.emptyList();
    }
    
    /**
     * Finds a collection field with the input name and wraps it in an ArrayList.
     * Returns Collections#emptyList if the field is not found.
     */
    @IndirectCallers
    public static <E> Collection<E> getFieldCollectionWrapped(Object instance, String getter, String field) {
        return getFieldCollectionWrapped(instance,getter,field,null);
    }
    
    /**
     * Finds a collection field with the input name and wraps it with the input wrapper.
     * If the wrapper is null, the field with be wrapped in an ArrayList.
     * Returns Collections#emptyList if the field is not found.
     */
    public static <E> Collection<E> getFieldCollectionWrapped(Object instance, String getter,
            String field, Function<Collection<E>,Collection<E>> wrapper) {
        Collection<E> collection = invokeStatic(Hacks.class,getter,instance,field);
        if(Objects.isNull(collection)) return Collections.emptyList();
        return Objects.nonNull(wrapper) ? wrapper.apply(collection) : new ArrayList<>(collection);
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
     * Gets a field as a nonnulll List.
     * Returns Collections#emptyList if the field is not found.
     */
    @IndirectCallers
    public static <E> List<E> getFieldList(Object instance, String getter, String field) {
        List<E> list = invokeStatic(Hacks.class,getter,instance,field);
        return Objects.nonNull(list) ? list : Collections.emptyList();
    }
    
    /**
     * Finds a collection field with the input name and wraps it in an ArrayList.
     * Returns Collections#emptyList if the field is not found.
     */
    public static <E> List<E> getFieldListWrapped(Object instance, String getter, String field) {
        Collection<E> collection = invokeStatic(Hacks.class,getter,instance,field);
        return Objects.nonNull(collection) ? new ArrayList<>(collection) : Collections.emptyList();
    }
    
    /**
     * Gets a field as a nonnulll Map.
     * Returns Collections#emptyMap if the field is not found.
     */
    @IndirectCallers
    public static <K,V> Map<K,V> getFieldMap(Object target, String getter, String field) {
        Map<K,V> map = invokeStatic(Hacks.class,getter,target,field);
        return Objects.nonNull(map) ? map : Collections.emptyMap();
    }
    
    /**
     * Gets a field as a nonnulll Map.
     * Returns Collections#emptyMap if the field is not found.
     */
    @IndirectCallers
    public static <K,V> Map<K,V> getFieldMapWrapped(Object target, String getter, String field) {
        Map<K,V> map = invokeStatic(Hacks.class,getter,target,field);
        return Objects.nonNull(map) ? new HashMap<>(map) : Collections.emptyMap();
    }
    
    /**
     * Gets a field as a nonnulll List.
     * Returns Collections#emptyList if the field is not found.
     */
    @IndirectCallers
    public static <E> Set<E> getFieldSet(Object instance, String getter, String field) {
        Set<E> set = invokeStatic(Hacks.class,getter,instance,field);
        return Objects.nonNull(set) ? set : Collections.emptySet();
    }
    
    /**
     * Finds a collection field with the input name and wraps it in an ArrayList.
     * Returns Collections#emptyList if the field is not found.
     */
    @IndirectCallers
    public static <E> Set<E> getFieldSetWrapped(Object instance, String getter, String field) {
        Collection<E> collection = invokeStatic(Hacks.class,getter,instance,field);
        return Objects.nonNull(collection) ? new HashSet<>(collection) : Collections.emptySet();
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
        if(JVMHelper.isJava8()) {
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
    
    private static @Nullable Object getStaticComponentByName(String component) {
        switch(component) {
            case "classloader": case "classloaders": case "Classloader": case "Classloaders":
            case "ClassLoader": case "ClassLoaders": return ClassLoaders;
            case "class": case "classes": case "Class": case "Classes": return Classes;
            case "contructor": case "constructors": case "Contructor": case "Constructors": return Constructors;
            case "driver": case "Driver": return Driver;
            case "field": case "fields": case "Field": case "Fields": return Fields;
            case "method": case "methods": case "Method": case "Methods": return Methods;
            default: {
                LOGGER.error("Static component type not found {}",component);
                return null;
            }
        }
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
     * Double-layer invoke that returns the default value when the wrapped invoke returns null.
     * The default value will also be returned if the wrapped invoke throws an exception.
     * Uses Hacks#invoke or (if the target object is a class or string) Hacks#invokeStatic.
     */
    public static <T> T invokeDefault(T defVal, Object target, String method, Object ... args) {
        String hacksMethod = "invoke";
        if(target instanceof String || target instanceof Class<?>) hacksMethod+="Static";
        return invokeDefault(hacksMethod,defVal,target,method,args);
    }
    
    /**
     * Double-layer invoke that returns the default value when the wrapped invoke returns null.
     * The default value will also be returned if the wrapped invoke throws an exception.
     * Special case:
     *      If the wrapped invoke returns an Optional, the value it contains will be returned.
     *      If the optional is empty, the default value will be returned,
     *      If the value is also an optional, it will be unwrapped until a non-optional or null value is found
     */
    public static <T> T invokeDefault(String hacksMethod, T defVal, Object target, String method, Object ... args) {
        try {
            Object ret = invokeStatic(Hacks.class,hacksMethod,target,method,args);
            if(Objects.isNull(ret)) return defVal;
            while(ret instanceof Optional<?>) ret = ((Optional<?>)ret).orElse(GenericUtils.cast(defVal));
            return GenericUtils.cast(ret);
        } catch(Throwable t) {
            LOGGER.error("Failed to invoke {} using Hacks#{}! Returning default value {}",
                         method,hacksMethod,defVal,t);
        }
        return defVal;
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
    
    @IndirectCallers
    public static <T> T invokeOnComponent(String componentName, String method, Object ... args) {
        Object component = getStaticComponentByName(componentName);
        if(Objects.isNull(component)) {
            LOGGER.error("Cannot invoke {} on null component! (args = {})",method,args);
            return null;
        }
        return invoke(component,method,args);
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
    
    public static ByteBuffer toByteBuffer(InputStream stream) {
        return Streams.toByteBuffer(stream);
    }
    
    public enum CallStrategy {
        
        DEFAULT("",Object.class),
        DIRECT("Direct",Object.class),
        STATIC("Static",Class.class),
        STATIC_DIRECT("StaticDirect",Class.class);
        
        private static final Map<String,CallStrategy> ALIAS_CACHE = new HashMap<>();
        
        public static CallStrategy byName(String strategy) {
            if(Objects.isNull(strategy)) return DEFAULT;
            if(ALIAS_CACHE.containsKey(strategy)) return ALIAS_CACHE.get(strategy);
            switch(strategy) {
                case "direct": case "Direct": case "DIRECT": {
                    ALIAS_CACHE.put(strategy,DIRECT);
                    return DIRECT;
                }
                case "static": case "Static": case "STATIC": {
                    ALIAS_CACHE.put(strategy,STATIC);
                    return STATIC;
                }
                default: {
                    if("both".equalsIgnoreCase(strategy) ||
                       Misc.equalsAnyIgnoreCase(strategy,Misc.wordCombinations("direct","static",""," ","_","-"))) {
                        ALIAS_CACHE.put(strategy,STATIC_DIRECT);
                        return STATIC_DIRECT;
                    }
                    ALIAS_CACHE.put(strategy,DEFAULT);
                    return DEFAULT;
                }
            }
        }
        
        @IndirectCallers
        public static <T> T callWith(String strategy, String s, Object ... args) {
            return byName(strategy).call(s, args);
        }
        
        final String ext;
        final Class<?> targetType;
        
        CallStrategy(final String ext, final Class<?> targetType) {
            this.ext = ext;
            this.targetType = targetType;
        }
        
        public <T> T call(String s, Object ... args) {
            return invokeStatic(Hacks.class,s+this.ext,args);
        }
        
        public <T> T get(Object ... args) {
            return call("getField",args);
        }
        
        public <T> T invoke(Object ... args) {
            return call("invoke",args);
        }
        
        public void set(Object ... args) {
            call("setField",args);
        }
    }
}