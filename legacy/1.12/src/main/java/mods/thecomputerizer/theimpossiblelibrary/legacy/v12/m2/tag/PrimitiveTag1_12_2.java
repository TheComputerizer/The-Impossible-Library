package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import net.minecraft.nbt.NBTPrimitive;

public class PrimitiveTag1_12_2 extends PrimitiveTagAPI<NBTPrimitive> {

    public PrimitiveTag1_12_2(NBTPrimitive tag) {
        super(tag);
    }
    
    @Override public boolean asBoolean() {
        return this.wrapped.getByte()==(byte)1;
    }

    @Override public byte asByte() {
        return this.wrapped.getByte();
    }
    
    @Override public CompoundTagAPI<?> asCompoundTag() {
        return null;
    }

    @Override public double asDouble() {
        return this.wrapped.getDouble();
    }

    @Override public float asFloat() {
        return this.wrapped.getFloat();
    }

    @Override public int asInt() {
        return this.wrapped.getInt();
    }
    
    @Override public ListTagAPI<?> asListTag() {
        return null;
    }

    @Override public long asLong() {
        return this.wrapped.getLong();
    }
    
    @Override public PrimitiveTagAPI<?> asPrimitiveTag() {
        return this;
    }

    @Override public short asShort() {
        return this.wrapped.getShort();
    }
    
    @Override public StringTagAPI<?> asStringTag() {
        return null;
    }
    
    @Override public boolean isCompound() {
        return false;
    }
    
    @Override public boolean isList() {
        return false;
    }
    
    @Override public boolean isPrimitive() {
        return true;
    }
    
    @Override public boolean isString() {
        return false;
    }
}
