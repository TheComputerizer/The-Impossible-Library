package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import net.minecraft.nbt.StringTag;

public class StringTag1_18_2 extends StringTagAPI<StringTag> {

    public StringTag1_18_2(StringTag tag) {
        super(tag);
    }
    
    @Override public CompoundTag1_18_2 asCompoundTag() {
        return null;
    }
    
    @Override public ListTag1_18_2 asListTag() {
        return null;
    }
    
    @Override public PrimitiveTag1_18_2 asPrimitiveTag() {
        return null;
    }
    
    @Override public StringTag1_18_2 asStringTag() {
        return this;
    }
    
    @Override public boolean isCompound() {
        return false;
    }
    
    @Override public boolean isList() {
        return false;
    }
    
    @Override public boolean isPrimitive() {
        return false;
    }
    
    @Override public boolean isString() {
        return true;
    }

    @Override public String getValue() {
        return this.wrapped.getAsString();
    }
}