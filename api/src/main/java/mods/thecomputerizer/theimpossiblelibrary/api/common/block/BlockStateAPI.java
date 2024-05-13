package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import lombok.Getter;

import javax.annotation.Nullable;

@SuppressWarnings("unused") @Getter
public abstract class BlockStateAPI<S> {

    protected final S state;

    protected BlockStateAPI(S state) {
        this.state = state;
    }

    public abstract BlockAPI<?> getBlock();
    public abstract MaterialAPI<?> getMaterial();
    public abstract @Nullable BlockPropertyAPI<?,?> getProperty(String name);
    
    @SuppressWarnings("unchecked")
    public boolean getPropertyBool(String name) {
        return getPropertyBool((BlockPropertyAPI<?,Boolean>)getProperty(name));
    }
    
    public abstract boolean getPropertyBool(BlockPropertyAPI<?,Boolean> property);
    
    @SuppressWarnings("unchecked")
    public <E extends Enum<E>> E getPropertyEnum(String name) {
        return getPropertyEnum((BlockPropertyAPI<?,E>)getProperty(name));
    }
    
    public abstract <E extends Enum<E>> E getPropertyEnum(BlockPropertyAPI<?,E> property);
    
    @SuppressWarnings("unchecked")
    public <V extends Comparable<V>> V getPropertyValue(String name) {
        return getPropertyValue((BlockPropertyAPI<?,V>)getProperty(name));
    }
    
    public abstract <V extends Comparable<V>> V getPropertyValue(BlockPropertyAPI<?,V> property);
    
    @SuppressWarnings("unchecked")
    public <V extends Comparable<V>> BlockStateAPI<?> withProperty(String name, V value) {
        return withProperty((BlockPropertyAPI<?,V>)getProperty(name),value);
    }
    
    public abstract <V extends Comparable<V>> BlockStateAPI<?> withProperty(BlockPropertyAPI<?,V> property, V value);
}