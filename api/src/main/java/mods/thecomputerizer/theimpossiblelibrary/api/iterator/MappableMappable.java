package mods.thecomputerizer.theimpossiblelibrary.api.iterator;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Wrapper used for bundling generic helper methods with a Mappable instnace that has Mappable values
 */
public class MappableMappable<K,L,V> extends Mappable<K,Mappable<L,V>> {

    public static <K,L,V> MappableMappable<K,L,V> makeSynchronizedMappable(
            Supplier<Map<K,Mappable<L,V>>> mapSupplier) {
        return new MappableMappable<>(mapSupplier.get(),true);
    }

    public static <K,L,V> MappableMappable<K,L,V> makeSynchronizedMappable(
            Supplier<Map<K,Mappable<L,V>>> mapSupplier, Consumer<MappableMappable<K,L,V>> settings) {
        MappableMappable<K,L,V> mappable = makeSynchronizedMappable(mapSupplier);
        settings.accept(mappable);
        return mappable;
    }

    public static <K,L,V> MappableMappable<K,L,V> makeMappable(
            Supplier<Map<K,Mappable<L,V>>> mapSupplier) {
        return new MappableMappable<>(mapSupplier.get(),false);
    }

    public static <K,L,V> MappableMappable<K,L,V> makeMappable(
            Supplier<Map<K,Mappable<L,V>>> mapSupplier,Consumer<MappableMappable<K,L,V>> settings) {
        MappableMappable<K,L,V> mappable = makeMappable(mapSupplier);
        settings.accept(mappable);
        return mappable;
    }

    public MappableMappable(Map<K,Mappable<L,V>> map, boolean isSynchronized) {
        super(map,isSynchronized);
    }
}