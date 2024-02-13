package mods.thecomputerizer.theimpossiblelibrary.api.tag;

public interface CompoundTagAPI extends BaseTagAPI {

    boolean contains(String key);
    CompoundTagAPI getCompoundTag(String key);
    ListTagAPI getListTag(String key);
    PrimitiveTagAPI getPrimitiveTag(String key);
    String getString(String key);
    BaseTagAPI getTag(String key);
    void putInt(String key, int value);
    void putString(String key, String value);
    void putTag(String key, BaseTagAPI tag);
}