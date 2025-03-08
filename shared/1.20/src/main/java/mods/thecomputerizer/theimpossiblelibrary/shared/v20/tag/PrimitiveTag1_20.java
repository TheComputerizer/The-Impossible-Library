package mods.thecomputerizer.theimpossiblelibrary.shared.v20.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import net.minecraft.nbt.NumericTag;

public class PrimitiveTag1_20 extends PrimitiveTagAPI<NumericTag> {

    public PrimitiveTag1_20(NumericTag tag) {
        super(tag);
    }

    @Override public boolean asBoolean() {
        return this.wrapped.getAsByte()==(byte)1;
    }

    @Override public byte asByte() {
        return this.wrapped.getAsByte();
    }
    
    @Override public CompoundTag1_20 asCompoundTag() {
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
    
    @Override public ListTag1_20 asListTag() {
        return null;
    }

    @Override public long asLong() {
        return this.wrapped.getAsLong();
    }
    
    @Override public PrimitiveTag1_20 asPrimitiveTag() {
        return this;
    }

    @Override public short asShort() {
        return this.wrapped.getAsShort();
    }
    
    @Override public StringTag1_20 asStringTag() {
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