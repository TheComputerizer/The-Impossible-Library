package mods.thecomputerizer.theimpossiblelibrary.api.data;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;

/**
 * Stores types for base, list, and compound tags
 */
public interface TagAPI<B,L extends B,C extends B> {

    @SuppressWarnings("unchecked")
    static <B,L extends B,C extends B> TagAPI<B,L,C> getInstance() {
        return (TagAPI<B,L,C>)CommonEntryPoint.INSTANCE.getTypes().getTagAPI();
    }

    B asBaseTag(Object tag);
    L asListTag(Object tag);
    C asCompundTag(Object tag);
    int getInt(C tag, String key);
    B getTag(C tag, String key);
    C getCompundTag(C tag, String key);
    void putInt(C compound, String key, int i);
    void putTag(C compound, String key, B tag);
}