package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import net.minecraft.nbt.NBTPrimitive;

public class PrimitiveTag1_12_2 extends BaseTag1_12_2<NBTPrimitive> implements PrimitiveTagAPI<NBTPrimitive> {

    public PrimitiveTag1_12_2(NBTPrimitive tag) {
        super(tag);
    }

    @Override
    public boolean asBoolean() {
        return this.wrapped.getByte()==(byte)1;
    }

    @Override
    public byte asByte() {
        return this.wrapped.getByte();
    }

    @Override
    public double asDouble() {
        return this.wrapped.getDouble();
    }

    @Override
    public float asFloat() {
        return this.wrapped.getFloat();
    }

    @Override
    public int asInt() {
        return this.wrapped.getInt();
    }

    @Override
    public long asLong() {
        return this.wrapped.getLong();
    }

    @Override
    public short asShort() {
        return this.wrapped.getShort();
    }
}
