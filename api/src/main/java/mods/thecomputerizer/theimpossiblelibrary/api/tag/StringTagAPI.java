package mods.thecomputerizer.theimpossiblelibrary.api.tag;

public abstract class StringTagAPI<T> extends BaseTagAPI<T> {
    
    protected StringTagAPI(Object tag) {
        super(tag);
    }
    
    @Override public CompoundTagAPI<?> asCompoundTag() {
        return null;
    }
    
    @Override public ListTagAPI<?> asListTag() {
        return null;
    }
    
    @Override public PrimitiveTagAPI<?> asPrimitiveTag() {
        return null;
    }
    
    @Override public StringTagAPI<?> asStringTag() {
        return this;
    }
    
    public abstract String getValue();
    
    @Override public boolean isCompound() {
        return false;
    }
    
    @Override public boolean isList() {
        return false;
    }
    
    @Override public boolean isPrimitive() {
        return false;
    }
    
    @Override public boolean isString() {
        return true;
    }
}