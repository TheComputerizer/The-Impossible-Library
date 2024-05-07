package mods.thecomputerizer.theimpossiblelibrary.api.iterator;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;

import javax.annotation.Nullable;
import java.util.*;
import java.util.Map.Entry;

/**
 * Helper methods, for instances of iterators, iterables, and maps
 */
@SuppressWarnings("unused") public class IterableHelper {

    public static <E> int count(Iterable<E> itr) {
        if(itr instanceof Collection<?>) return ((Collection<?>)itr).size();
        if(itr instanceof Wrapperable<?>) return ((Wrapperable<?>)itr).size();
        if(itr instanceof Mappable<?,?>) return ((Mappable<?,?>)itr).size();
        return count(itr.iterator());
    }
    
    /**
     This will consume the iterator, so use it with caution!
     */
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
    
    /**
     Runs non-strict equality rules.
     If both inputs are null, they are considered matching.
     If the value is an instance of an array, iterable, or iterator, further matching rules are applied.
     If the iterable only has one element that matches the value, they are considered matching
     */
    public static boolean matches(@Nullable Iterable<?> itr, @Nullable Object value) {
        return Objects.isNull(itr) ? Objects.isNull(value) : matches(itr.iterator(),value);
    }
    
    /**
     Runs non-strict equality rules.
     If both inputs are null, they are considered matching.
     If the value is an instance of an array, iterable, or iterator, further matching rules are applied.
     If the iterator only has one element that matches the value, they are considered matching
     */
    public static boolean matches(@Nullable Iterator<?> itr, @Nullable Object value) {
        if(value instanceof Object[]) return matchesArray(itr,(Object[])value);
        if(value instanceof Iterable<?>) return matchesItr((Iterable<?>)value,itr);
        if(value instanceof Iterator<?>) return matchesItr(itr,(Iterator<?>)value);
        if(Objects.isNull(itr)) return Objects.isNull(value);
        if(Objects.isNull(value)) return false;
        boolean potential = false;
        int count = 0;
        while(itr.hasNext()) {
            Object next = itr.next();
            potential = count==0 && GenericUtils.matches(next,value);
            count++;
        }
        return potential;
    }
    
    /**
     Runs non-strict equality rules.
     If both inputs are null, they are considered matching.
     If the iterable has the same number of elements as the array, matching rules are run for each element.
     Ordering of the iterable instance is not guaranteed.
     */
    public static boolean matchesArray(@Nullable Iterable<?> itr, @Nullable Object[] array) {
        return Objects.isNull(itr) ? Objects.isNull(array) : matchesArray(itr.iterator(),array);
    }
    
    /**
     Runs non-strict equality rules.
     If both inputs are null, they are considered matching.
     If the iterable only has one element that matches the value, they are considered matching
     If the value is an instance of an array or other iterable further matching rules are applied.
     */
    public static boolean matchesArray(@Nullable Iterator<?> itr, @Nullable Object[] array) {
        if(Objects.isNull(itr)) return Objects.isNull(array);
        if(Objects.isNull(array)) return false;
        boolean potential = true;
        int count = 0;
        while(itr.hasNext()) {
            Object next = itr.next();
            if(array.length<=count) return false;
            if(!GenericUtils.matches(next,array[count])) {
                potential = false;
                break;
            }
            count++;
        }
        return potential && count==array.length;
    }
    
    /**
     Runs non-strict equality rules.
     If both inputs are null, they are considered matching.
     If the iterable only has one element that matches the value, they are considered matching
     If the value is an instance of an array or other iterable further matching rules are applied.
     */
    public static boolean matchesItr(@Nullable Iterable<?> itr, @Nullable Iterable<?> other) {
        return Objects.isNull(itr) ? Objects.isNull(other) : matchesItr(other,itr.iterator());
    }
    
    /**
     Runs non-strict equality rules.
     If both inputs are null, they are considered matching.
     If the iterable only has one element that matches the value, they are considered matching
     If the value is an instance of an array or other iterable further matching rules are applied.
     */
    public static boolean matchesItr(@Nullable Iterable<?> itr, @Nullable Iterator<?> other) {
        return Objects.isNull(itr) ? Objects.isNull(other) : matchesItr(itr.iterator(),other);
        
    }
    
    /**
     Runs non-strict equality rules.
     If both inputs are null, they are considered matching.
     If the iterable only has one element that matches the value, they are considered matching
     If the value is an instance of an array or other iterable further matching rules are applied.
     */
    public static boolean matchesItr(@Nullable Iterator<?> itr, @Nullable Iterator<?> other) {
        if(Objects.isNull(itr)) return Objects.isNull(other);
        if(Objects.isNull(other)) return false;
        while(itr.hasNext()) {
            Object next = itr.next();
            if(!other.hasNext() || !GenericUtils.matches(next,other.next())) return false;
        }
        return !other.hasNext();
    }
}