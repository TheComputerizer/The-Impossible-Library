package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

public class ListTag1_12_2 extends ListTagAPI<NBTTagList> {

    public ListTag1_12_2(NBTTagList tag) {
        super(tag);
    }
    
    @Override public void addTag(BaseTagAPI<?> tag) {
        this.wrapped.appendTag((NBTBase)tag.getWrapped());
    }
    
    @Override public CompoundTagAPI<?> asCompoundTag() {
        return null;
    }
    
    @Override public ListTagAPI<?> asListTag() {
        return this;
    }
    
    @Override public PrimitiveTagAPI<?> asPrimitiveTag() {
        return null;
    }
    
    @Override public StringTagAPI<?> asStringTag() {
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
