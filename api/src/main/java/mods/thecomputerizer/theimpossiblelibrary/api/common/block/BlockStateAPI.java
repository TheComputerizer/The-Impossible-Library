package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

import javax.annotation.Nullable;

public abstract class BlockStateAPI<S> extends AbstractWrapped<S> {

    protected BlockStateAPI(S state) {
        super(state);
    }

    public abstract BlockAPI<?> getBlock();
    public abstract MaterialAPI<?> getMaterial();
    public abstract @Nullable BlockPropertyAPI<?,?> getProperty(String name);
    
    @IndirectCallers @SuppressWarnings("unchecked")
    public boolean getPropertyBool(String name) {
        return getPropertyBool((BlockPropertyAPI<?,Boolean>)getProperty(name));
    }
    
    public abstract boolean getPropertyBool(BlockPropertyAPI<?,Boolean> property);
    
    @IndirectCallers @SuppressWarnings("unchecked")
    public <E extends Enum<E>> E getPropertyEnum(String name) {
        return getPropertyEnum((BlockPropertyAPI<?,E>)getProperty(name));
    }
    
    public abstract <E extends Enum<E>> E getPropertyEnum(BlockPropertyAPI<?,E> property);
    
    @IndirectCallers @SuppressWarnings("unchecked")
    public <V extends Comparable<V>> V getPropertyValue(String name) {
        return getPropertyValue((BlockPropertyAPI<?,V>)getProperty(name));
    }
    
    public abstract <V extends Comparable<V>> V getPropertyValue(BlockPropertyAPI<?,V> property);
    
    @IndirectCallers @SuppressWarnings("unchecked")
    public <V extends Comparable<V>> BlockStateAPI<?> withProperty(String name, V value) {
        return withProperty((BlockPropertyAPI<?,V>)getProperty(name),value);
    }
    
    public abstract <V extends Comparable<V>> BlockStateAPI<?> withProperty(BlockPropertyAPI<?,V> property, V value);
}