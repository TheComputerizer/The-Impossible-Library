package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.tag.CompoundTag1_20;

public class CompoundTag1_20_6 extends CompoundTag1_20 implements TagWrapper {

    public CompoundTag1_20_6(Object tag) {
        super(tag);
    }

    @Override public BaseTagAPI<?> getTag(String key) {
        return getIfNotNull(w -> TagHelper.getWrapped(w.get(key)));
    }
}