package mods.thecomputerizer.theimpossiblelibrary.api.util;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.iterator.DynamicArray;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Uncategorized util methods
 */
@SuppressWarnings("ALL")
public class Misc {

    public static boolean allNonNull(Object ... objects) {
        return !anyNull(objects);
    }


    public static boolean allNull(Object ... objects) {
        return !anyNonNull(objects);
    }

    public static boolean anyNonNull(Object ... objects) {
        for(Object obj : objects)
            if(Objects.nonNull(obj)) return true;
        return false;
    }

    public static boolean anyNull(Object ... objects) {
        for(Object obj : objects)
            if(Objects.isNull(obj)) return true;
        return false;
    }

    /**
     * Exceptions need to be handled externally
     */
    public static <K,V> @Nullable V applyNullable(@Nullable K thing, Function<K,V> function) {
        return Objects.isNull(thing) ? null : function.apply(thing);
    }

    /**
     * Returns the full name of the class of the object or NULL if the object is null.
     */
    public static String className(@Nullable Object obj) {
        return className(Objects.nonNull(obj) ? obj.getClass() : null,false);
    }

    /**
     * Returns the full name of the class or NULL if the class is null.
     */
    public static String className(@Nullable Class<?> clazz) {
        return className(clazz,false);
    }

    /**
     * Returns the either the full or simple name of the class of the object or NULL if the object is null.
     */
    public static String className(@Nullable Object obj, boolean simple) {
        return className(Objects.nonNull(obj) ? obj.getClass() : null,simple);
    }

    /**
     * Returns the either the full or simple name of the class or NULL if the class is null.
     */
    public static String className(@Nullable Class<?> clazz, boolean simple) {
        return Objects.nonNull(clazz) ? (simple ? clazz.getSimpleName() : clazz.getName()) : "NULL";
    }

    /**
     * Exceptions need to be handled externally
     */
    public static <K> void consumeNullable(@Nullable K thing, Consumer<K> conumer) {
        if(Objects.nonNull(thing)) conumer.accept(thing);
    }

    /**
     * Replaces file paths in a list that point to folders with a list of all files in that folder. File paths that
     * point to single files are still added file paths that don't exist are automatically removed
     */
    public static List<String> expandFilePaths(@Nullable List<String> filePaths) {
        Set<String> files = new HashSet<>();
        if(Objects.nonNull(filePaths)) {
            for(String filePath : filePaths) {
                try(Stream<Path> paths = Files.walk(Paths.get(filePath))) {
                    files.addAll(paths.filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList()));
                } catch(IOException ex) {
                    TILRef.logError("Failed to walk file path {}",filePath,ex);
                }
                File file = new File(filePath);
                if(filePath.contains(".")) files.add(filePath);
                else {
                    String[] nextFiles = file.list();
                    if(Objects.nonNull(nextFiles)){
                        for(String appendedPath : expandFilePaths(nextFiles))
                            files.add(filePath+"/"+appendedPath);
                    }
                }
            }
        }
        return new ArrayList<>(files);
    }

    public static List<String> expandFilePaths(String ... filePaths) {
        return expandFilePaths(Arrays.asList(filePaths));
    }

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

    /**
     * Probably should be avoided but serves as an easy way to unbox Boolean arrays
     */
    public static boolean[] fixBoxedArray(Boolean ... boxed) {
        boolean[] primitive = new boolean[boxed.length];
        for(int i=0; i<boxed.length; i++) primitive[i] = boxed[i];
        return primitive;
    }

    /**
     * Probably should be avoided but serves as an easy way to unbox Boolean arrays
     */
    public static byte[] fixBoxedArray(Byte ... boxed) {
        byte[] primitive = new byte[boxed.length];
        for(int i=0; i<boxed.length; i++) primitive[i] = boxed[i];
        return primitive;
    }

    /**
     * Probably should be avoided but serves as an easy way to unbox Character arrays
     */
    public static char[] fixBoxedArray(Character ... boxed) {
        char[] primitive = new char[boxed.length];
        for(int i=0; i<boxed.length; i++) primitive[i] = boxed[i];
        return primitive;
    }

    /**
     * Probably should be avoided but serves as an easy way to unbox Double arrays
     */
    public static double[] fixBoxedArray(Double ... boxed) {
        double[] primitive = new double[boxed.length];
        for(int i=0; i<boxed.length; i++) primitive[i] = boxed[i];
        return primitive;
    }

    /**
     * Probably should be avoided but serves as an easy way to unbox Float arrays
     */
    public static float[] fixBoxedArray(Float ... boxed) {
        float[] primitive = new float[boxed.length];
        for(int i=0; i<boxed.length; i++) primitive[i] = boxed[i];
        return primitive;
    }

    /**
     * Probably should be avoided but serves as an easy way to unbox Integer arrays
     */
    public static int[] fixBoxedArray(Integer ... boxed) {
        int[] primitive = new int[boxed.length];
        for(int i=0; i<boxed.length; i++) primitive[i] = boxed[i];
        return primitive;
    }

    /**
     * Probably should be avoided but serves as an easy way to unbox Long arrays
     */
    public static long[] fixBoxedArray(Long ... boxed) {
        long[] primitive = new long[boxed.length];
        for(int i=0; i<boxed.length; i++) primitive[i] = boxed[i];
        return primitive;
    }

    /**
     * Probably should be avoided but serves as an easy way to unbox Short arrays
     */
    public static short[] fixBoxedArray(Short ... boxed) {
        short[] primitive = new short[boxed.length];
        for(int i=0; i<boxed.length; i++) primitive[i] = boxed[i];
        return primitive;
    }

    public static <E> E[] fixObjParsedArray(Object[] array, Class<?> fixAs) {
        DynamicArray base = new DynamicArray(-1,fixAs);
        base = new DynamicArray(base.getBracketCount()-1,base.getBaseClass());
        for(int i=0; i<array.length; i++) array[i] = getFixedObject(array[i],base.getTypeClass()); //Handle nested arrays
        return (E[])supplyArrayCreation(base.getTypeClass(),array.length, i -> array[i]);
    }

    /**
     * Fixes instances of Object[] that come from generic parsing.
     */
    public static Object getFixedObject(Object obj, Class<?> fixAs) {
        if(obj instanceof Object[]) return fixObjParsedArray((Object[])obj,fixAs);
        try {
            return fixAs.cast(obj);
        } catch(ClassCastException ex) {
            return obj;
        }
    }

    public static <V> V getEither(boolean choice, V ifChoice, V notChoice) {
        return choice ? ifChoice : notChoice;
    }

    /**
     * if choice 1 else choice 2 else neither
     */
    public static <V> V getEitherOr(boolean choice1, boolean choice2, V ifChoice1, V ifChoice2, V neither) {
        return choice1 ? ifChoice1 : (choice2 ? ifChoice2 : neither);
    }

    /**
     * The returns arrays is be used as reference and any choice element not present at the index will be false.
     * Assumes returns will always be nonnull with at least 1 element
     */
    @SafeVarargs
    public static <V> V getEitherTrailing(boolean[] choices, V ... returns) {
        if(returns.length==1) return returns[0];
        for(int i=0; i+1<returns.length; i++) {
            boolean choice = choices.length>i && choices[i];
            if(choice) return returns[i];
        }
        return returns[returns.length-1];
    }

    public static @Nullable Field getField(@Nullable Class<?> clazz, String fieldName) {
        return Misc.applyNullable(clazz,c -> {
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

    public static @Nullable Object getFieldInstance(@Nullable Object parent, @Nullable Field field) {
        return Misc.applyNullable(field,f -> {
            try {
                return f.get(parent);
            } catch(IllegalAccessException ex) {
                TILRef.logError("Failed to retrieve instance of field {} of type {} from parent {} of class {}",
                        f,getNullable(f,f.getType(),"null"),parent,getNullable(parent,parent.getClass(),"null"));
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

    public static String getLastSplit(String str, String splitBy) {
        return str.substring(str.lastIndexOf(splitBy)+1);
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

    public static <N,V> V getNullable(@Nullable N nullable, V notNull, V isNull) {
        return getEither(Objects.nonNull(nullable),notNull,isNull);
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

    public static <T> Object listToArray(List<T> list) {
        return listToArray(list,new ArrayList<>(Collections.singletonList(list.size())));
    }

    public static <T> Object listToArray(List<T> list, List<Integer> dimensions) {
        if(list.isEmpty()) return makeArray(Object.class,0);
        T first = list.get(0);
        if(first instanceof List<?>) {
            List<?> next = ((List<?>)first);
            dimensions.add(next.size());
            return listToArray(next,dimensions);
        } else return makeArray(first.getClass(), fixBoxedArray(dimensions.toArray(new Integer[0])));
    }

    /**
     * Adds a trimmed lowercase string a collection after ensuring it is not null, empty, or blank
     */
    public static void lowerCaseAddCollection(Collection<String> c, String str) {
        if(StringUtils.isNotBlank(str)) c.add(str.trim().toLowerCase());
    }

    /**
     * Adds a trimmed lowercase string a map key after ensuring it is not null, empty, or blank
     */
    public static <V> void lowerCaseAddMap(Map<String,V> map, String str, V val) {
        if(StringUtils.isNotBlank(str)) map.put(str.trim().toLowerCase(),val);
    }

    public static Object makeArray(Class<?> clazz, int ... dimensions) {
        int[] dims = new int[dimensions.length];
        System.arraycopy(dimensions,0,dims,0,dims.length);
        try {
            return Array.newInstance(clazz,dims);
        } catch (IllegalArgumentException | NegativeArraySizeException ex) {
            TILRef.logError("Failed to instantiate array of class {} with dimensions {}",clazz,dimensions,ex);
            return new Object();
        }
    }

    public static String removeAll(String str, String ... removals) {
        for(String removal : removals) str = str.replaceAll(removal,"");
        return str;
    }

    public static <E> void supplyArray(E[] array, Function<Integer,E> func) {
        for(int i=0; i<array.length; i++) array[i] = func.apply(i);
    }

    public static <E,F> void supplyArray(E[] array, F thing, BiFunction<F,Integer,E> func) {
        for(int i=0; i<array.length; i++) array[i] = func.apply(thing,i);
    }

    public static <E> Object supplyArrayCreation(Class<E> clazz, int size, Function<Integer,?> func) {
        E[] array = (E[])Array.newInstance(clazz,size);
        for(int i=0; i<array.length; i++) array[i] = (E)func.apply(i);
        return array;
    }

    public static <E,F> Object supplyArrayCreation(Class<E> clazz, int size, F thing, BiFunction<F,Integer,?> func) {
        E[] array = (E[])Array.newInstance(clazz,size);
        for(int i=0; i<array.length; i++) array[i] = (E)func.apply(thing,i);
        return array;
    }

    public static <V,W> @Nullable W wrap(@Nullable V val, Function<V,W> wrapperFunc) {
        return applyNullable(val,wrapperFunc);
    }
}
