package mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import net.minecraft.nbt.StringTag;

public class StringTag1_21 extends StringTagAPI<StringTag> {

    public StringTag1_21(StringTag tag) {
        super(tag);
    }
    
    @Override public CompoundTag1_21 asCompoundTag() {
        return null;
    }
    
    @Override public ListTag1_21 asListTag() {
        return null;
    }
    
    @Override public PrimitiveTag1_21 asPrimitiveTag() {
        return null;
    }
    
    @Override public StringTag1_21 asStringTag() {
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