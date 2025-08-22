package mods.thecomputerizer.theimpossiblelibrary.api.wrappers;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreStateAccessor;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Wrapped<W> extends CoreStateAccessor {
    
    W getWrapped();
    
    default <V> @Nullable V getIfNotNull(Function<W,V> getter) {
        W wrapped = getWrapped();
        return Objects.nonNull(wrapped) ? getter.apply(wrapped) : null;
    }
    
    default <V> V getIfNotNullOrDefault(Function<W,V> getter, V defaultValue) {
        Supplier<V> defaultValueSupplier = () -> defaultValue;
        return getIfNotNullOrDefault(getter,defaultValueSupplier);
    }
    
    default <V> V getIfNotNullOrDefault(Function<W,V> getter, Supplier<V> defaultValueSupplier) {
        W wrapped = getWrapped();
        return Objects.nonNull(wrapped) ? getter.apply(wrapped) : defaultValueSupplier.get();
    }
    
    default Class<W> getWrappedClass() {
        return GenericUtils.cast(getWrapped().getClass());
    }
    
    @IndirectCallers
    default boolean isNull() {
        return Objects.isNull(getWrapped());
    }
    
    @IndirectCallers
    default boolean isNullGetter(Function<W,?> getter) {
        W wrapped = getWrapped();
        return Objects.isNull(wrapped) || Objects.isNull(getter.apply(wrapped));
    }
    
    @IndirectCallers
    default boolean notNull() {
        return Objects.nonNull(getWrapped());
    }
    
    @IndirectCallers
    default boolean notNullGetter(Function<W,?> getter) {
        W wrapped = getWrapped();
        return Objects.nonNull(wrapped) && Objects.nonNull(getter.apply(wrapped));
    }
    
    @IndirectCallers
    default <V> @Nullable V supplyIfNull(Function<W,V> getter, Supplier<V> ifNullSupplier) {
        V value = getIfNotNull(getter);
        return Objects.nonNull(value) ? value : ifNullSupplier.get();
    }
    
    default <U> U unwrap() {
        return GenericUtils.cast(getWrapped());
    }
    
    default <V,R> @Nullable R wrapIfNotNull(Function<W,V> getter, Function<V,R> wrapper) {
        V value = getIfNotNull(getter);
        return Objects.nonNull(value) ? wrapper.apply(value) : null;
    }
    
    @IndirectCallers
    default <V,R> R wrapIfNotNullOrDefault(Function<W,V> getter, Function<V,R> wrapper, R defaultValue) {
        Supplier<R> defaultValueSupplier = () -> defaultValue;
        return wrapIfNotNullOrDefault(getter,wrapper,defaultValueSupplier);
    }
    
    @IndirectCallers
    default <V,R> R wrapIfNotNullOrDefault(Function<W,V> getter, Function<V,R> wrapper,
            Supplier<R> defaultValueSupplier) {
        V value = getIfNotNull(getter);
        return Objects.nonNull(value) ? wrapper.apply(value) : defaultValueSupplier.get();
    }
}