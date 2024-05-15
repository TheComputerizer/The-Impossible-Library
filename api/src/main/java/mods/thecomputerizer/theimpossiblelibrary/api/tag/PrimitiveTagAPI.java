package mods.thecomputerizer.theimpossiblelibrary.api.tag;

@SuppressWarnings("unused")
public abstract class PrimitiveTagAPI<T> extends BaseTagAPI<T> {
    
    protected PrimitiveTagAPI(T tag) {
        super(tag);
    }
    
    public abstract boolean asBoolean();
    public abstract byte asByte();
    public abstract double asDouble();
    public abstract float asFloat();
    public abstract int asInt();
    public abstract long asLong();
    public abstract short asShort();
}