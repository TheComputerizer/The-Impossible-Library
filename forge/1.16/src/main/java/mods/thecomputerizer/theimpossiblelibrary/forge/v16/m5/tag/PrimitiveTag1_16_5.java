package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import net.minecraft.nbt.NumberNBT;

public class PrimitiveTag1_16_5 extends BaseTag1_16_5<NumberNBT> implements PrimitiveTagAPI<NumberNBT> {

    public PrimitiveTag1_16_5(NumberNBT tag) {
        super(tag);
    }

    @Override
    public boolean asBoolean() {
        return this.wrapped.getAsByte()==(byte)1;
    }

    @Override
    public byte asByte() {
        return this.wrapped.getAsByte();
    }

    @Override
    public double asDouble() {
        return this.wrapped.getAsDouble();
    }

    @Override
    public float asFloat() {
        return this.wrapped.getAsFloat();
    }

    @Override
    public int asInt() {
        return this.wrapped.getAsInt();
    }

    @Override
    public long asLong() {
        return this.wrapped.getAsLong();
    }

    @Override
    public short asShort() {
        return this.wrapped.getAsShort();
    }
}
