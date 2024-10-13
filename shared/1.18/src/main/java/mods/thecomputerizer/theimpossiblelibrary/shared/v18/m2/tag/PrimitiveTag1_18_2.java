package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import net.minecraft.nbt.NumericTag;

public class PrimitiveTag1_18_2 extends PrimitiveTagAPI<NumericTag> {

    public PrimitiveTag1_18_2(NumericTag tag) {
        super(tag);
    }

    @Override public boolean asBoolean() {
        return this.wrapped.getAsByte()==(byte)1;
    }

    @Override public byte asByte() {
        return this.wrapped.getAsByte();
    }
    
    @Override public CompoundTag1_18_2 asCompoundTag() {
        return null;
    }

    @Override public double asDouble() {
        return this.wrapped.getAsDouble();
    }

    @Override public float asFloat() {
        return this.wrapped.getAsFloat();
    }

    @Override public int asInt() {
        return this.wrapped.getAsInt();
    }
    
    @Override public ListTag1_18_2 asListTag() {
        return null;
    }

    @Override public long asLong() {
        return this.wrapped.getAsLong();
    }
    
    @Override public PrimitiveTag1_18_2 asPrimitiveTag() {
        return this;
    }

    @Override public short asShort() {
        return this.wrapped.getAsShort();
    }
    
    @Override public StringTag1_18_2 asStringTag() {
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