package mods.thecomputerizer.theimpossiblelibrary.api.tag;

@SuppressWarnings("unused")
public abstract class CompoundTagAPI<T> extends BaseTagAPI<T> {
    
    protected CompoundTagAPI(T tag) {
        super(tag);
    }
    
    public abstract boolean contains(String key);
    public abstract CompoundTagAPI<?> getCompoundTag(String key);
    public abstract ListTagAPI<?> getListTag(String key);
    public abstract PrimitiveTagAPI<?> getPrimitiveTag(String key);
    public abstract String getString(String key);
    public abstract BaseTagAPI<?> getTag(String key);
    public abstract void putBoolean(String key, boolean b);
    public abstract void putByte(String key, byte b);
    public abstract void putDouble(String key, double d);
    public abstract void putFloat(String key, float f);
    public abstract void putInt(String key, int i);
    public abstract void putLong(String key, long l);
    public abstract void putShort(String key, short s);
    public abstract void putString(String key, String value);
    public abstract void putTag(String key, BaseTagAPI<?> tag);
}