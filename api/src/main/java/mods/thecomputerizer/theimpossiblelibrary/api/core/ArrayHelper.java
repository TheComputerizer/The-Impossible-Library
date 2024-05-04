package mods.thecomputerizer.theimpossiblelibrary.api.core;

import mods.thecomputerizer.theimpossiblelibrary.api.iterator.DynamicArray;
import mods.thecomputerizer.theimpossiblelibrary.api.iterator.IterableHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings("unused")
public class ArrayHelper {

    @SuppressWarnings("unchecked")
    public static <T> T[] append(T[] original, T toAppend, boolean allowDuplicates) {
        if(!allowDuplicates && ArrayUtils.contains(original,toAppend)) return original;
        if(Objects.isNull(toAppend)) {
            TILRef.logError("Cannot append null value to array! Use Misc#expandArray to do that.");
            return original;
        }
        T[] expanded = expand(original,(Class<T>)toAppend.getClass());
        expanded[expanded.length-1] = toAppend;
        return expanded;
    }

    public static <E> int countOccurances(E[] array, E occurance) {
        int count = 0;
        for(E element : array)
            if((Objects.isNull(element) && Objects.isNull(occurance)) || element.equals(occurance)) count++;
        return count;
    }

    /**
     * Creates an array with the input dimensions. Ensures the length is at least 0.
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] create(Class<T> clazz, int length) {
        return (T[])Array.newInstance(clazz,Math.max(length,0));
    }

    /**
     * Creates a potentially multidimensional array with the input dimensions. Return nulls if an exception is caught
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] createMulti(Class<T> clazz, int[] lengths) {
        if(Objects.isNull(lengths) || lengths.length==0) return create(clazz,0);
        if(lengths.length==1) return create(clazz,lengths[0]);
        for(int i=0;i<lengths.length;i++) lengths[i] = Math.max(lengths[i],0);
        try {
            return (T[])Array.newInstance(clazz,lengths);
        } catch (IllegalArgumentException ex) {
            TILRef.logError("Failed to instantiate array of class {} with dimensions {}",clazz,lengths,ex);
            return null;
        }
    }

    /**
     * Removes all duplicate values from the input array.
     */
    @SuppressWarnings("UnusedReturnValue") @SafeVarargs
    public static <T> T[] deduplicate(T[] array, T ... valuesToIgnore) {
        return deduplicate(array,false,null,valuesToIgnore);
    }

    /**
     * Removes all duplicate values from the input array.
     * If replaceValues is enabled,
     * any duplicate values after the first instance will be replaced with the input replacement.
     */
    @SafeVarargs
    public static <T> T[] deduplicate(T[] array, boolean replaceValues, T replacement, T ... valuesToIgnore) {
        Boolean[] replacements = new Boolean[array.length];
        for(int i=0;i<array.length;i++) {
            T element = array[i];
            if(hasElement(valuesToIgnore,element)) continue;
            int next = findFirstOccuranceAfter(array,element,i);
            if(next!=-1) replacements[next] = true;
        }
        int newLength = array.length-countOccurances(replacements,true);
        int first = findFirstOccurance(replacements,true);
        while(first!=-1) {
            if(replaceValues) {
                array[first] = replacement;
                replacements[first] = false;
            } else {
                removeElement(array,first);
                removeElement(replacements,first);
            }
            first = findFirstOccurance(replacements,true);
        }
        return array;
    }

    /**
     * Increases the size of the array by 1. The new element will be null or the default value for primitives.
     * Calling this with an original that is null or has a length of 0 will still return an array with one element.
     */
    public static <T> T[] expand(T[] original, Class<T> clazz) {
        int length = Objects.nonNull(original) ? original.length : 0;
        T[] expanded = create(clazz,length+1);
        if(length>=1) System.arraycopy(original,0,expanded,0,length);
        return expanded;
    }

    /**
     * Returns -1 if the element is not found in the array
     */
    public static <E> int findFirstOccurance(E[] array, E element) {
        return findFirstOccuranceAfter(array,element,-1);
    }

    /**
     * Returns -1 if the element is not found in the array after the index
     */
    public static <E> int findFirstOccuranceAfter(E[] array, E element, int index) {
        if(Objects.isNull(array) || array.length==0) return -1;
        for(int i=index+1;i<array.length;i++)
            if(array[i]==element) return i;
        return -1;
    }

    /**
     * Probably should be avoided but serves as an easy way to unbox Boolean arrays
     */
    public static boolean[] fixBoxedPrimitve(Boolean ... boxed) {
        boolean[] primitive = new boolean[boxed.length];
        for(int i=0; i<boxed.length; i++) primitive[i] = boxed[i];
        return primitive;
    }

    /**
     * Probably should be avoided but serves as an easy way to unbox Boolean arrays
     */
    public static byte[] fixBoxedPrimitve(Byte ... boxed) {
        byte[] primitive = new byte[boxed.length];
        for(int i=0; i<boxed.length; i++) primitive[i] = boxed[i];
        return primitive;
    }

    /**
     * Probably should be avoided but serves as an easy way to unbox Character arrays
     */
    public static char[] fixBoxedPrimitve(Character ... boxed) {
        char[] primitive = new char[boxed.length];
        for(int i=0; i<boxed.length; i++) primitive[i] = boxed[i];
        return primitive;
    }

    /**
     * Probably should be avoided but serves as an easy way to unbox Double arrays
     */
    public static double[] fixBoxedPrimitve(Double ... boxed) {
        double[] primitive = new double[boxed.length];
        for(int i=0; i<boxed.length; i++) primitive[i] = boxed[i];
        return primitive;
    }

    /**
     * Probably should be avoided but serves as an easy way to unbox Float arrays
     */
    public static float[] fixBoxedPrimitve(Float ... boxed) {
        float[] primitive = new float[boxed.length];
        for(int i=0; i<boxed.length; i++) primitive[i] = boxed[i];
        return primitive;
    }

    /**
     * Probably should be avoided but serves as an easy way to unbox Integer arrays
     */
    public static int[] fixBoxedPrimitve(Integer ... boxed) {
        int[] primitive = new int[boxed.length];
        for(int i=0; i<boxed.length; i++) primitive[i] = boxed[i];
        return primitive;
    }

    /**
     * Probably should be avoided but serves as an easy way to unbox Long arrays
     */
    public static long[] fixBoxedPrimitve(Long ... boxed) {
        long[] primitive = new long[boxed.length];
        for(int i=0; i<boxed.length; i++) primitive[i] = boxed[i];
        return primitive;
    }

    /**
     * Probably should be avoided but serves as an easy way to unbox Short arrays
     */
    public static short[] fixBoxedPrimitve(Short ... boxed) {
        short[] primitive = new short[boxed.length];
        for(int i=0; i<boxed.length; i++) primitive[i] = boxed[i];
        return primitive;
    }

    @SuppressWarnings("unchecked")
    public static <E> E[] fixObjParsed(Object[] array, Class<?> fixAs) {
        DynamicArray base = new DynamicArray(-1,fixAs);
        base = new DynamicArray(base.getBracketCount()-1,base.getBaseClass());
        for(int i=0; i<array.length; i++) array[i] = Misc.getFixedObject(array[i],base.getTypeClass()); //Handle nested arrays
        return (E[])supplyArrayCreation(base.getTypeClass(),array.length,i -> array[i]);
    }

    public static <E> E[] forEach(E[] array, BiConsumer<E,Integer> consumer) {
        if(Objects.nonNull(array))
            for(int i=0;i<array.length;i++)
                consumer.accept(array[i],i);
        return array;
    }

    @SuppressWarnings("unchecked") public static <T> T[] fromIterable(Iterable<?> itr, Class<T> clazz) {
        int[] lengths = IterableHelper.getLengths(itr);
        return (T[])fromIterator(itr.iterator(),clazz,createMulti(clazz,lengths));
    }

    @SuppressWarnings("unchecked") public static <T> T[] fromIterator(Iterator<?> itr, Class<T> clazz) {
        int[] lengths = IterableHelper.getLengths(itr);
        return (T[])fromIterator(itr,clazz,createMulti(clazz,lengths));
    }

    private static <T> Object[] fromIterator(Iterator<?> itr, Class<T> clazz, Object[] array) {
        int i=0;
        while(itr.hasNext()) {
            Object element = itr.next();
            if(element instanceof Iterable<?>) array[i] = fromIterable((Iterable<?>)element,clazz);
            else if(element instanceof Iterator<?>) array[i] = fromIterator((Iterator<?>)element,clazz);
            else array[i] = element;
            i++;
        }
        return array;
    }

    public static <E> boolean hasElement(E[] array, E element) {
        for(E e : array)
            if(e==element) return true;
        return false;
    }

    public static <E> boolean hasElementAfter(E[] array, E element, int index) {
        for(int i=index+1;i<array.length;i++)
            if(array[i]==element) return true;
        return false;
    }

    public static <E> boolean hasElementExceptAt(E[] array, E element, int index) {
        for(int i=0;i<array.length;i++) {
            if(i==index) continue;
            if(array[i]==element) return true;
        }
        return false;
    }

    public static <E> boolean isEmpty(E[] array) {
        return Objects.isNull(array) || array.length==0;
    }

    public static <E> boolean isNotEmpty(E[] array) {
        return !isEmpty(array);
    }

    public static <E,T> T[] mapTo(E[] array, Class<T> clazz, Function<E,T> remapper) {
        if(Objects.isNull(array)) return null;
        int length = array.length;
        T[] remapped = create(clazz,length);
        for(int i=0;i<length;i++) remapped[i] = remapper.apply(array[i]);
        return remapped;
    }

    public static <E> E[] removeAllOccurancesAfter(E[] array, E element, int after) {
        if(Objects.nonNull(array) && after<array.length-1) {
            int index = findFirstOccuranceAfter(array,element,after);
            while (index != -1) {
                array = removeElement(array,index);
                index = findFirstOccurance(array,element);
            }
        }
        return array;
    }

    public static <E> E[] removeAllOccurancesOf(E[] array, E element) {
        return removeAllOccurancesAfter(array,element,-1);
    }

    @SuppressWarnings("unchecked")
    public static <E> E[] removeElement(E[] array, int index) {
        if(Objects.nonNull(array) && array.length>0) {
            int length = array.length;
            for(int i=index;i<length-1;i++) array[i] = array[i+1];
            E[] removed = create((Class<E>)array.getClass().getComponentType(),length-1);
            System.arraycopy(array,0,removed,0,length-1);
            array = removed;
        }
        return array;
    }
    
    @SuppressWarnings("unchecked") public static <E> E[] removeMatching(E[] array, E toMatch, Function<E,Boolean> matcher) {
        if(isNotEmpty(array)) {
            List<E> copy = new ArrayList<>();
            for(E element : array)
                if(!matcher.apply(element)) copy.add(element);
            if(copy.size()<array.length) array = (E[])fromIterable(copy,toMatch.getClass());
        }
        return array;
    }

    public static <E> void supplyArray(E[] array, Function<Integer,E> func) {
        for(int i=0; i<array.length; i++) array[i] = func.apply(i);
    }

    public static <E,F> void supplyArray(E[] array, F thing, BiFunction<F,Integer,E> func) {
        for(int i=0; i<array.length; i++) array[i] = func.apply(thing,i);
    }

    @SuppressWarnings("unchecked")
    public static <E> Object supplyArrayCreation(Class<E> clazz, int size, Function<Integer,?> func) {
        E[] array = create(clazz,size);
        for(int i=0; i<array.length; i++) array[i] = (E)func.apply(i);
        return array;
    }

    @SuppressWarnings("unchecked")
    public static <E,F> Object supplyArrayCreation(Class<E> clazz, int size, F thing, BiFunction<F,Integer,?> func) {
        E[] array = create(clazz,size);
        for(int i=0; i<array.length; i++) array[i] = (E)func.apply(thing,i);
        return array;
    }
}
