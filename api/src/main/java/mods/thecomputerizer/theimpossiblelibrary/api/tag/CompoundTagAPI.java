package mods.thecomputerizer.theimpossiblelibrary.api.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.util.Wrapped;

@SuppressWarnings("unused")
public interface CompoundTagAPI<T> extends Wrapped<T> {

    boolean contains(String key);
    CompoundTagAPI<?> getCompoundTag(String key);
    ListTagAPI<?> getListTag(String key);
    PrimitiveTagAPI<?> getPrimitiveTag(String key);
    String getString(String key);
    BaseTagAPI<?> getTag(String key);
    void putBoolean(String key, boolean b);
    void putByte(String key, byte b);
    void putDouble(String key, double d);
    void putFloat(String key, float f);
    void putInt(String key, int i);
    void putLong(String key, long l);
    void putShort(String key, short s);
    void putString(String key, String value);
    void putTag(String key, BaseTagAPI<?> tag);
    void putTag(String key, CompoundTagAPI<?> tag);
    void putTag(String key, ListTagAPI<?> tag);
    void putTag(String key, PrimitiveTagAPI<?> tag);
    void putTag(String key, StringTagAPI<?> tag);
}