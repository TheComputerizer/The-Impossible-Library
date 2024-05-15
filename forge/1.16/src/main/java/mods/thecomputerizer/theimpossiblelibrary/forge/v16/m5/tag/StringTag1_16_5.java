package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import net.minecraft.nbt.StringNBT;

public class StringTag1_16_5 extends StringTagAPI<StringNBT> {

    public StringTag1_16_5(StringNBT tag) {
        super(tag);
    }
    
    @Override
    public CompoundTag1_16_5 asCompoundTag() {
        return null;
    }
    
    @Override
    public ListTag1_16_5 asListTag() {
        return null;
    }
    
    @Override
    public PrimitiveTag1_16_5 asPrimitiveTag() {
        return null;
    }
    
    @Override
    public StringTag1_16_5 asStringTag() {
        return this;
    }
    
    @Override
    public boolean isCompound() {
        return false;
    }
    
    @Override
    public boolean isList() {
        return false;
    }
    
    @Override
    public boolean isPrimitive() {
        return false;
    }
    
    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public String getValue() {
        return this.wrapped.getAsString();
    }
}
