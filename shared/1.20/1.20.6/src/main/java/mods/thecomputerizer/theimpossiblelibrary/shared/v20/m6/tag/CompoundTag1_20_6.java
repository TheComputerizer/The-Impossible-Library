package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.tag.CompoundTag1_20;
import net.minecraft.nbt.CompoundTag;

public class CompoundTag1_20_6 extends CompoundTag1_20 implements TagWrapper {

    public CompoundTag1_20_6(CompoundTag tag) {
        super(tag);
    }
    
    @Override public CompoundTag1_20_6 asCompoundTag() {
        return this;
    }
    
    @Override public ListTag1_20_6 asListTag() {
        return null;
    }

    @Override public CompoundTag1_20_6 getCompoundTag(String key) {
        return new CompoundTag1_20_6(this.wrapped.getCompound(key));
    }

    @Override public ListTag1_20_6 getListTag(String key) {
        return (ListTag1_20_6)getTag(key).asListTag();
    }

    @Override public BaseTagAPI<?> getTag(String key) {
        return TagHelper.getWrapped(this.wrapped.get(key));
    }
}