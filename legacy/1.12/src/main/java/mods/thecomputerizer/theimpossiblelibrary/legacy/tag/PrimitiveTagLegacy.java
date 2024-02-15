package mods.thecomputerizer.theimpossiblelibrary.legacy.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import net.minecraft.nbt.NBTPrimitive;

public class PrimitiveTagLegacy extends BaseTagLegacy<NBTPrimitive> implements PrimitiveTagAPI {

    protected PrimitiveTagLegacy(NBTPrimitive tag) {
        super(tag);
    }

    @Override
    public boolean asBoolean() {
        return this.tag.getByte()==1;
    }

    @Override
    public byte asByte() {
        return this.tag.getByte();
    }

    @Override
    public double asDouble() {
        return this.tag.getDouble();
    }

    @Override
    public float asFloat() {
        return this.tag.getFloat();
    }

    @Override
    public int asInt() {
        return this.tag.getInt();
    }

    @Override
    public long asLong() {
        return this.tag.getLong();
    }

    @Override
    public short asShort() {
        return this.tag.getShort();
    }
}