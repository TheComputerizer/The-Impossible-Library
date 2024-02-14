package mods.thecomputerizer.theimpossiblelibrary.api.tag;

/**
 * Stores types for base, list, and compound tags
 */
public interface BaseTagAPI {

    CompoundTagAPI asCompoundTag();
    ListTagAPI asListTag();
    boolean isCompound();
    boolean isList();
    boolean isString();
    boolean isPrimitive();
}