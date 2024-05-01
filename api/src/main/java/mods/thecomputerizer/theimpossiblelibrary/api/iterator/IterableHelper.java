package mods.thecomputerizer.theimpossiblelibrary.api.iterator;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;

import javax.annotation.Nullable;
import java.util.*;
import java.util.Map.Entry;

/**
 * Helper methods, for instances of iterators, iterables, and maps
 */
public class IterableHelper {

    public static <E> int count(Iterable<E> itr) {
        if(itr instanceof Collection<?>) return ((Collection<?>)itr).size();
        if(itr instanceof Wrapperable<?>) return ((Wrapperable<?>)itr).size();
        if(itr instanceof Mappable<?,?>) return ((Mappable<?,?>)itr).size();
        return count(itr.iterator());
    }

    public static <E> int count(Iterator<E> itr) {
        int count = 0;
        while(itr.hasNext()) {
            count++;
            itr.next();
        }
        return count;
    }

    public static <E> int countOccurances(E occurance, Iterable<E> itr) {
        return countOccurances(occurance,itr.iterator());
    }

    public static <E> int countOccurances(E occurance, Iterator<E> itr) {
        int count = 0;
        while(itr.hasNext()) {
            E element = itr.next();
            if((Objects.isNull(element) && Objects.isNull(occurance)) || element.equals(occurance)) count++;
        }
        return count;
    }

    /**
     * Does not verify ordering!
     */
    public static <E> E getElement(int index, Iterable<E> itr) throws IndexOutOfBoundsException {
        if(itr instanceof List<?>) return ((List<E>)itr).get(index);
        if(itr instanceof Wrapperable<?>) return ((Wrapperable<E>)itr).get(index);
        return getElement(index,itr.iterator());
    }

    /**
     * Does not verify ordering!
     */
    public static <E> E getElement(int index, Iterator<E> itr) throws IndexOutOfBoundsException {
        if(index<0) throw new IndexOutOfBoundsException("Cannot get negative index `"+index+"` for iterator");
        int counter = index;
        while(itr.hasNext()) {
            E element = itr.next();
            if (counter == 0) return element;
            index--;
        }
        throw new IndexOutOfBoundsException("Index `"+index+"` is out of bounds for iterator of size `"+(index-counter)+"`");
    }

    /**
     * Does not verify ordering!
     */
    public static <E> @Nullable E getElementOrDefault(int index, E defaultVal, Iterable<E> itr) {
        return getElementOrDefault(index,defaultVal,itr.iterator());
    }

    /**
     * Does not verify ordering!
     */
    public static <E> E getElementOrDefault(int index, E defaultVal, Iterator<E> itr) {
        try {
            return getElement(index,itr);
        } catch(IndexOutOfBoundsException ex) {
            TILRef.logError("Failed to get iterator element.",ex);
            return defaultVal;
        }
    }

    /**
     * Does not verify ordering!
     */
    public static <E> @Nullable E getElementOrNull(int index, Iterable<E> itr) {
        return getElementOrNull(index,itr.iterator());
    }

    /**
     * Does not verify ordering!
     */
    public static <E> @Nullable E getElementOrNull(int index, Iterator<E> itr) {
        return getElementOrDefault(index,null,itr);
    }

    /**
     * Iterates over the input iterable while checking if there are any iterable or iterator elements.
     * If an iterable or iterator is found then the size of the largest will be used as the next length
     */
    public static int[] getLengths(Iterable<?> itr) {
        return getLengths(itr.iterator());
    }

    /**
     * Iterates over the input while checking if there are any iterable or iterator elements.
     * If an iterable or iterator is found then the size of the largest will be used as the next length
     */
    public static int[] getLengths(Iterator<?> itr) {
        return getLengths(itr,0,new int[]{0});
    }

    private static int[] getLengths(Iterator<?> itr, int index, int[] lengths) {
        if(index>=lengths.length) {
            int[] lengthsCopy = new int[index+1];
            System.arraycopy(lengths,0,lengthsCopy,0,lengths.length);
            lengths = lengthsCopy;
        }
        int size = 0;
        while(itr.hasNext()) {
            Object element = itr.next();
            if(element instanceof Iterable<?>)
                lengths = getLengths(((Iterable<?>)element).iterator(),index+1,lengths);
            else if(element instanceof Iterator<?>)
                lengths = getLengths((Iterator<?>)element,index+1,lengths);
            size++;
        }
        lengths[index] = Math.max(lengths[index],size);
        return lengths;
    }
    
    public static <K,V> Entry<K,V> getMapEntry(K key, V val) {
        return getMapEntry(key,val,false);
    }

    public static <K,V> Entry<K,V> getMapEntry(K key, V val, boolean mutable) {
        return mutable ? new MutablePair<>(key,val) : new ImmutablePair<>(key,val);
    }
}