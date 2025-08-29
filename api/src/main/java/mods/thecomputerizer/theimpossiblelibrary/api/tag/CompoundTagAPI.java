package mods.thecomputerizer.theimpossiblelibrary.api.tag;

import java.util.Objects;

@SuppressWarnings("unused")
public abstract class CompoundTagAPI<T> extends BaseTagAPI<T> {
    
    protected CompoundTagAPI(Object tag) {
        super(tag);
    }
    
    @Override public CompoundTagAPI<?> asCompoundTag() {
        return this;
    }
    
    @Override public ListTagAPI<?> asListTag() {
        return null;
    }
    
    @Override public PrimitiveTagAPI<?> asPrimitiveTag() {
        return null;
    }
    
    @Override public StringTagAPI<?> asStringTag() {
        return null;
    }
    
    public abstract boolean contains(String key);
    
    public CompoundTagAPI<?> getCompoundTag(String key) {
        if(!contains(key)) return null;
        return getTag(key).asCompoundTag();
    }
    
    public ListTagAPI<?> getListTag(String key) {
        if(!contains(key)) return null;
        return getTag(key).asListTag();
    }
    
    public PrimitiveTagAPI<?> getPrimitiveTag(String key) {
        if(!contains(key)) return null;
        return getTag(key).asPrimitiveTag();
    }
    
    public String getString(String key) {
        if(!contains(key)) return null;
        StringTagAPI<?> tag = getTag(key).asStringTag();
        return Objects.nonNull(tag) ? tag.getValue() : null;
    }
    
    public abstract BaseTagAPI<?> getTag(String key);
    
    @Override public boolean isCompound() {
        return true;
    }
    
    public abstract boolean isEmpty();
    
    @Override public boolean isList() {
        return false;
    }
    
    @Override public boolean isPrimitive() {
        return false;
    }
    
    @Override public boolean isString() {
        return false;
    }
    
    public void putBoolean(String key, boolean b) {
        putTag(key,TagHelper.makePrimitiveTag(b));
    }
    
    public void putByte(String key, byte b) {
        putTag(key,TagHelper.makePrimitiveTag(b));
    }
    
    public void putDouble(String key, double d) {
        putTag(key,TagHelper.makePrimitiveTag(d));
    }
    
    public void putFloat(String key, float f) {
        putTag(key,TagHelper.makePrimitiveTag(f));
    }
    
    public void putInt(String key, int i) {
        putTag(key,TagHelper.makePrimitiveTag(i));
    }
    
    public void putLong(String key, long l) {
        putTag(key,TagHelper.makePrimitiveTag(l));
    }
    
    public void putShort(String key, short s) {
        putTag(key,TagHelper.makePrimitiveTag(s));
    }
    
    public void putString(String key, String value) {
        putTag(key,TagHelper.makeStringTag(value));
    }
    
    public abstract void putTag(String key, BaseTagAPI<?> tag);
    public abstract String toPrettyString();
}