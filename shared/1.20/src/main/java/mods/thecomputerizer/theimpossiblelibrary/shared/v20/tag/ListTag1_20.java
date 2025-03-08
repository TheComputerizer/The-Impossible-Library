package mods.thecomputerizer.theimpossiblelibrary.shared.v20.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.List;

public class ListTag1_20 extends ListTagAPI<ListTag> {

    public ListTag1_20(ListTag tag) {
        super(tag);
    }

    @Override public void addTag(BaseTagAPI<?> tag) {
        this.wrapped.add((Tag)tag.getWrapped());
    }
    
    @Override public CompoundTag1_20 asCompoundTag() {
        return null;
    }
    
    @Override public ListTag1_20 asListTag() {
        return this;
    }
    
    @Override public PrimitiveTag1_20 asPrimitiveTag() {
        return null;
    }
    
    @Override public StringTag1_20 asStringTag() {
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