package mods.thecomputerizer.theimpossiblelibrary.api.iterator;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Wrapper used for bundling generic helper methods with a Mappable instance that has Wrapperable values
 */
public class WrapperableMappable<K,V> extends Mappable<K,Wrapperable<V>> {

    public WrapperableMappable(Map<K,Wrapperable<V>> map, boolean isSynchronized) {
        super(map,isSynchronized);
    }

    public void add(@Nullable K key, V element) {
        Wrapperable<V> val = get(key);
        if(Objects.nonNull(val)) val.add(element);
    }

    /**
     * Adds the element to the wrapperable value at the key or adds the input value and then adds the element to it
     */
    public void addIfAbsent(@Nullable K key, Wrapperable<V> val, V element) {
        val = putIfAbsent(key,val);
        if(Objects.nonNull(val)) val.add(element);
    }

    /**
     * Returns true if the value was not found and was successfully inserted
     */
    public boolean addIfUnmatched(@Nullable K key, Wrapperable<V> val, V element) {
        if(Objects.isNull(key) || matchesAnyValue(element)) return false;
        addIfAbsent(key,val,element);
        return true;
    }

    /**
     * Uses for loops to return true if the input function evaluates to true
     */
    public boolean checkAllValues(Function<V,Boolean> func) {
        for(Wrapperable<V> values : values())
            for(V value : values)
                if(func.apply(value)) return true;
        return false;
    }

    /**
     * forEach can be used if nothing needs to be returned
     */
    public void forAllValues(Consumer<V> consumer) {
        values().forEach(wrapperable -> wrapperable.forEach(consumer));
    }

    public K getKeyFromMatchingValue(Function<V,Boolean> matcher, K defVal) {
        for(Entry<K,Wrapperable<V>> entry : this)
            for(V val : entry.getValue())
                if(matcher.apply(val)) return entry.getKey();
        return defVal;
    }

    public V getMatchingValue(Function<V,Boolean> matcher, V defVal) {
        for(Wrapperable<V> values : values())
            for(V value : values)
                if(matcher.apply(value)) return value;
        return defVal;
    }

    /**
     * Adds all the values from the wrapperable values of the map to the output collection
     */
    public void insertFlatValues(Collection<V> output) {
        forAllValues(output::add);
    }

    public <C extends Collection<V>> C insertFlatValues(Supplier<C> outputSupplier) {
        C output = outputSupplier.get();
        forAllValues(output::add);
        return output;
    }

    public void insertMatchingValues(Collection<V> output, Function<V,Boolean> matcher) {
        forAllValues(val -> {
            if(matcher.apply(val)) output.add(val);
        });
    }

    public <C extends Collection<V>> C insertMatchingValues(Supplier<C> outputSupplier, Function<V,Boolean> matcher) {
        C output = outputSupplier.get();
        forAllValues(val -> {
            if(matcher.apply(val)) output.add(val);
        });
        return output;
    }

    /**
     * Checks if the input value matches any value under any key
     */
    public boolean matchesAnyValue(V other) {
        return checkAllValues(val -> Objects.equals(val,other));
    }

    public boolean matchesNone(V other) {
        return !matchesAnyValue(other);
    }

    public Wrapperable<V> putFast(@Nullable K key, Supplier<Iterable<V>> wrappableSupplier) {
        return putFast(key,wrappableSupplier.get());
    }

    public Wrapperable<V> putFast(@Nullable K key, Iterable<V> itr) {
        if(Objects.isNull(key)) return new Wrapperable<>(itr,false);
        return put(key,new Wrapperable<>(itr,false));
    }

    public Stream<V> parallelStreamAllValues() {
        return values().parallelStream().flatMap(Wrapperable::parallelStream);
    }

    public Stream<V> streamAllValues() {
        return values().stream().flatMap(Wrapperable::stream);
    }
}
