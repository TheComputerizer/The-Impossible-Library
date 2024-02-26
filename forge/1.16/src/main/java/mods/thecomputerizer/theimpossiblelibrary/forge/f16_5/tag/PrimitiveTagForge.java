package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import net.minecraft.nbt.NumberNBT;

public class PrimitiveTagForge extends BaseTagForge<NumberNBT> implements PrimitiveTagAPI {

    protected PrimitiveTagForge(NumberNBT tag) {
        super(tag);
    }

    @Override
    public boolean asBoolean() {
        return this.tag.getAsByte()==1;
    }

    @Override
    public byte asByte() {
        return this.tag.getAsByte();
    }

    @Override
    public double asDouble() {
        return this.tag.getAsDouble();
    }

    @Override
    public float asFloat() {
        return this.tag.getAsFloat();
    }

    @Override
    public int asInt() {
        return this.tag.getAsInt();
    }

    @Override
    public long asLong() {
        return this.tag.getAsLong();
    }

    @Override
    public short asShort() {
        return this.tag.getAsShort();
    }
}
