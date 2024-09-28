package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import net.minecraft.nbt.NBTTagString;

public class StringTag1_12_2 extends StringTagAPI<NBTTagString> {

    public StringTag1_12_2(NBTTagString tag) {
        super(tag);
    }
    
    @Override public CompoundTagAPI<?> asCompoundTag() {
        return null;
    }
    
    @Override public ListTagAPI<?> asListTag() {
        return null;
    }
    
    @Override public PrimitiveTagAPI<?> asPrimitiveTag() {
        return null;
    }
    
    @Override public StringTagAPI<?> asStringTag() {
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
        return this.wrapped.getString();
    }
}
