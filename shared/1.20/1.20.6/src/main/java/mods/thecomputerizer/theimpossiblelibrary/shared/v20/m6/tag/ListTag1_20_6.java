package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.tag.ListTag1_20;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.List;

public class ListTag1_20_6 extends ListTag1_20 implements TagWrapper {

    public ListTag1_20_6(ListTag tag) {
        super(tag);
    }

    @Override public void addTag(BaseTagAPI<?> tag) {
        this.wrapped.add((Tag)tag.getWrapped());
    }
    
    @Override public CompoundTag1_20_6 asCompoundTag() {
        return null;
    }
    
    @Override public ListTag1_20_6 asListTag() {
        return this;
    }
    
    @Override public Iterable<BaseTagAPI<?>> iterable() {
        List<BaseTagAPI<?>> tags = new ArrayList<>();
        this.wrapped.forEach(based -> tags.add(TagHelper.getWrapped(based)));
        return tags;
    }
}