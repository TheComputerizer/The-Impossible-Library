package mods.thecomputerizer.theimpossiblelibrary.api.tag;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

@SuppressWarnings("unused")
public abstract class ListTagAPI<T> extends BaseTagAPI<T> implements Iterable<BaseTagAPI<?>> {
    
    protected ListTagAPI(Object tag) {
        super(tag);
    }
    
    public abstract void addTag(BaseTagAPI<?> tag);
    
    @Override public CompoundTagAPI<?> asCompoundTag() {
        return null;
    }
    
    @Override public ListTagAPI<?> asListTag() {
        return this;
    }
    
    @Override public PrimitiveTagAPI<?> asPrimitiveTag() {
        return null;
    }
    
    @Override public StringTagAPI<?> asStringTag() {
        return null;
    }
    
    @Override public boolean isCompound() {
        return false;
    }
    
    @Override public boolean isList() {
        return true;
    }
    
    @Override public boolean isPrimitive() {
        return false;
    }
    
    @Override public boolean isString() {
        return false;
    }
    
    public abstract Iterable<BaseTagAPI<?>> iterable();
    
    @Override public @NotNull Iterator<BaseTagAPI<?>> iterator() {
        return iterable().iterator();
    }
}