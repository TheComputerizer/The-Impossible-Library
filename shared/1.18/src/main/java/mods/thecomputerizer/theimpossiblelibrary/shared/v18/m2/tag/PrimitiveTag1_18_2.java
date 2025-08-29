package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import net.minecraft.nbt.NumericTag;

public class PrimitiveTag1_18_2 extends PrimitiveTagAPI<NumericTag> {

    public PrimitiveTag1_18_2(Object tag) {
        super(tag);
    }
    
    @Override public byte asByte() {
        return getIfNotNullOrDefault(NumericTag::getAsByte,(byte)0);
    }
    
    @Override public double asDouble() {
        return getIfNotNullOrDefault(NumericTag::getAsDouble,0d);
    }
    
    @Override public float asFloat() {
        return getIfNotNullOrDefault(NumericTag::getAsFloat,0f);
    }
    
    @Override public int asInt() {
        return getIfNotNullOrDefault(NumericTag::getAsInt,0);
    }
    
    @Override public long asLong() {
        return getIfNotNullOrDefault(NumericTag::getAsLong,0L);
    }
    
    @Override public short asShort() {
        return getIfNotNullOrDefault(NumericTag::getAsShort,(short)0);
    }
}