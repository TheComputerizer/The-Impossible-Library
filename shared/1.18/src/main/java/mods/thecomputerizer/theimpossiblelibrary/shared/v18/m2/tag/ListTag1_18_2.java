package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.List;

public class ListTag1_18_2 extends ListTagAPI<ListTag> {

    public ListTag1_18_2(ListTag tag) {
        super(tag);
    }

    @Override public void addTag(BaseTagAPI<?> tag) {
        this.wrapped.add((Tag)tag.getWrapped());
    }
    
    @Override public CompoundTag1_18_2 asCompoundTag() {
        return null;
    }
    
    @Override public ListTag1_18_2 asListTag() {
        return this;
    }
    
    @Override public PrimitiveTag1_18_2 asPrimitiveTag() {
        return null;
    }
    
    @Override public StringTag1_18_2 asStringTag() {
        return null;
    }
    
    @Override public boolean isCompound() {
        return false;
    }
    
    @Override public boolean isList() {
        return true;
    }
    
    @Override public boolean isPrimitive() {
        return false;
    }
    
    @Override public boolean isString() {
        return false;
    }
    
    @Override public Iterable<BaseTagAPI<?>> iterable() {
        List<BaseTagAPI<?>> tags = new ArrayList<>();
        this.wrapped.forEach(based -> tags.add(TagHelper.getWrapped(based)));
        return tags;
    }
}