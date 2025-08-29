package mods.thecomputerizer.theimpossiblelibrary.api.tag;

@SuppressWarnings("unused")
public abstract class PrimitiveTagAPI<T> extends BaseTagAPI<T> {
    
    protected PrimitiveTagAPI(Object tag) {
        super(tag);
    }
    
    public boolean asBoolean() {
        return asByte()==(byte)1;
    }
    
    public abstract byte asByte();
    
    @Override public CompoundTagAPI<?> asCompoundTag() {
        return null;
    }
    
    public abstract double asDouble();
    public abstract float asFloat();
    public abstract int asInt();
    
    @Override public ListTagAPI<?> asListTag() {
        return null;
    }
    
    public abstract long asLong();
    
    @Override public PrimitiveTagAPI<?> asPrimitiveTag() {
        return this;
    }
    
    public abstract short asShort();
    
    @Override public StringTagAPI<?> asStringTag() {
        return null;
    }
    
    @Override public boolean isCompound() {
        return false;
    }
    
    @Override public boolean isList() {
        return false;
    }
    
    @Override public boolean isPrimitive() {
        return true;
    }
    
    @Override public boolean isString() {
        return false;
    }
}