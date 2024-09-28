package mods.thecomputerizer.theimpossiblelibrary.api.iterator;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Wrapper used for bundling generic helper methods with a final Map instance
 */
@ParametersAreNonnullByDefault
public class Mappable<K,V> implements Map<K,V>, Iterable<Map.Entry<K,V>> {

    public static <K,V> Mappable<K,V> make(Supplier<Map<K,V>> supplier) {
        return new Mappable<>(supplier.get(),false);
    }

    @IndirectCallers
    public static <K,V> Mappable<K,V> make(Supplier<Map<K,V>> supplier, Consumer<Mappable<K,V>> settings) {
        Mappable<K,V> mappable = make(supplier);
        settings.accept(mappable);
        return mappable;
    }

    public static <K,V> Mappable<K,V> makeSynchronized(Supplier<Map<K,V>> supplier) {
        return new Mappable<>(supplier.get(),true);
    }
    
    @IndirectCallers
    public static <K,V> Mappable<K,V> makeSynchronized(Supplier<Map<K,V>> supplier, Consumer<Mappable<K,V>> settings) {
        Mappable<K,V> mappable = make(supplier);
        settings.accept(mappable);
        return mappable;
    }

    private final Map<K,V> map;

    public Mappable(Map<K,V> map, boolean isSynchronized) {
        this.map = isSynchronized ? Collections.synchronizedMap(map) : map;
    }

    @Override public void clear() {
        this.map.clear();
    }

    @Override public @Nullable V compute(@Nullable K key, BiFunction<? super K, ? super V, ? extends V> remappingFunc) {
        if(Objects.isNull(key)) return null;
        return this.map.computeIfPresent(key,remappingFunc);
    }

    @Override public @Nullable V computeIfAbsent(@Nullable K key, Function<? super K, ? extends V> mappingFunc) {
        if(Objects.isNull(key)) return null;
        return this.map.computeIfAbsent(key,mappingFunc);
    }

    @Override public @Nullable V computeIfPresent(@Nullable K key, BiFunction<? super K, ? super V, ? extends V> remappingFunc) {
        if(Objects.isNull(key)) return null;
        return this.map.computeIfPresent(key,remappingFunc);
    }

    @Override public boolean containsKey(@Nullable Object key) {
        return Objects.nonNull(key) && this.map.containsKey(key);
    }

    @Override public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override public @Nonnull Set<Entry<K,V>> entrySet() {
        return this.map.entrySet();
    }

    @Override public void forEach(Consumer<? super Entry<K,V>> action) {
        entrySet().forEach(action);
    }
    
    @IndirectCallers
    public void forEachKey(Consumer<? super K> action) {
        keySet().forEach(action);
    }
    
    @IndirectCallers
    public void forEachValue(Consumer<? super V> action) {
        values().forEach(action);
    }

    @Override public @Nullable V get(@Nullable Object key) {
        return Objects.nonNull(key) ? this.map.get(key) : null;
    }

    public K getKeyOrDefault(Function<V,Boolean> matcher, K defVal) {
        for(Entry<K,V> entry : this)
            if(matcher.apply(entry.getValue()))
                return entry.getKey();
        return defVal;
    }

    @Override public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    @Override public @Nonnull Iterator<Entry<K,V>> iterator() {
        return entrySet().iterator();
    }
    
    @IndirectCallers
    public boolean keyAbsent(@Nullable Object key) {
        return Objects.isNull(key) || !containsKey(key);
    }

    @Override public @Nonnull Set<K> keySet() {
        return this.map.keySet();
    }
    
    @IndirectCallers
    public Stream<Entry<K,V>> parallelStream() {
        return entrySet().parallelStream();
    }

    @Override public @Nullable V put(@Nullable K key, V value) {
        return Objects.nonNull(key) ? this.map.put(key,value) : null;
    }

    @Override public void putAll(Map<? extends K, ? extends V> otherMap) {
        this.map.putAll(otherMap);
    }

    @Override public @Nullable V putIfAbsent(@Nullable K key, V value) {
        if(Objects.isNull(key)) return null;
        return this.map.putIfAbsent(key,value);
    }

    @Override public @Nullable V remove(@Nullable Object key) {
        return Objects.nonNull(key) ? this.map.remove(key) : null;
    }

    @Override public int size() {
        return this.map.size();
    }

    @Override public Spliterator<Entry<K,V>> spliterator() {
        return entrySet().spliterator();
    }

    public Stream<Entry<K,V>> stream() {
        return entrySet().stream();
    }

    @Override public String toString() {
        return this.map.toString();
    }

    @Override public @Nonnull Collection<V> values() {
        return this.map.values();
    }
}
