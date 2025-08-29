package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class BlockStateAPI<S> extends AbstractWrapped<S> {

    protected BlockStateAPI(Object state) {
        super(state);
    }

    public abstract BlockAPI<?> getBlock();
    public abstract MaterialAPI<?> getMaterial();
    public abstract @Nullable BlockPropertyAPI<?,?> getProperty(String name);
    
    @IndirectCallers
    public boolean getPropertyBool(String name) {
        BlockPropertyAPI<?,Boolean> property = GenericUtils.cast(getProperty(name));
        return Objects.nonNull(property) && getPropertyBool(property);
    }
    
    public abstract boolean getPropertyBool(BlockPropertyAPI<?,Boolean> property);
    
    @IndirectCallers
    public <E extends Enum<E>> E getPropertyEnum(String name) {
        BlockPropertyAPI<?,E> property = GenericUtils.cast(getProperty(name));
        return Objects.nonNull(property) ? getPropertyEnum(property) : null;
    }
    
    public abstract <E extends Enum<E>> E getPropertyEnum(BlockPropertyAPI<?,E> property);
    
    @IndirectCallers
    public <V extends Comparable<V>> V getPropertyValue(String name) {
        BlockPropertyAPI<?,V> property = GenericUtils.cast(getProperty(name));
        return Objects.nonNull(property) ? getPropertyValue(property) : null;
    }
    
    public abstract <V extends Comparable<V>> V getPropertyValue(BlockPropertyAPI<?,V> property);
    
    @IndirectCallers
    public <V extends Comparable<V>> BlockStateAPI<?> withProperty(String name, V value) {
        BlockPropertyAPI<?,V> property = GenericUtils.cast(getProperty(name));
        return Objects.nonNull(property) ? withProperty(property,value) : null;
    }
    
    public abstract <V extends Comparable<V>> BlockStateAPI<?> withProperty(BlockPropertyAPI<?,V> property, V value);
}