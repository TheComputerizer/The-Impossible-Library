package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import net.minecraft.nbt.NBTPrimitive;

public class PrimitiveTag1_12_2 extends PrimitiveTagAPI<NBTPrimitive> {

    public PrimitiveTag1_12_2(Object tag) {
        super(tag);
    }

    @Override public byte asByte() {
        return getIfNotNullOrDefault(NBTPrimitive::getByte,(byte)0);
    }

    @Override public double asDouble() {
        return getIfNotNullOrDefault(NBTPrimitive::getDouble,0d);
    }

    @Override public float asFloat() {
        return getIfNotNullOrDefault(NBTPrimitive::getFloat,0f);
    }

    @Override public int asInt() {
        return getIfNotNullOrDefault(NBTPrimitive::getInt,0);
    }

    @Override public long asLong() {
        return getIfNotNullOrDefault(NBTPrimitive::getLong,0L);
    }

    @Override public short asShort() {
        return getIfNotNullOrDefault(NBTPrimitive::getShort,(short)0);
    }
}
