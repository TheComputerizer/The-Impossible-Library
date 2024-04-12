package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.tag;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import net.minecraft.nbt.*;

@Getter
public class BaseTag1_16_5<N extends INBT> implements BaseTagAPI {

    protected final N tag;

    public BaseTag1_16_5(N tag) {
        this.tag = tag;
    }

    @Override
    public CompoundTagAPI asCompoundTag() {
        if(this instanceof CompoundTagAPI) return (CompoundTagAPI)this;
        if(this.tag instanceof CompoundNBT) return new CompoundTag1_16_5((CompoundNBT)this.tag);
        return null;
    }

    @Override
    public ListTagAPI asListTag() {
        if(this instanceof ListTagAPI) return (ListTagAPI)this;
        if(this.tag instanceof ListNBT) return new ListTag1_16_5((ListNBT)this.tag);
        return null;
    }

    @Override
    public PrimitiveTag1_16_5 asPrimitiveTag() {
        if(this instanceof PrimitiveTag1_16_5) return (PrimitiveTag1_16_5)this;
        if(this.tag instanceof NumberNBT) return new PrimitiveTag1_16_5((NumberNBT)this.tag);
        return null;
    }

    @Override
    public StringTag1_16_5 asStringTag() {
        if(this instanceof StringTag1_16_5) return (StringTag1_16_5)this;
        if(this.tag instanceof StringNBT) return new StringTag1_16_5((StringNBT)this.tag);
        return null;
    }

    @Override
    public boolean isCompound() {
        return this instanceof CompoundTag1_16_5 || this.tag instanceof CompoundNBT;
    }

    @Override
    public boolean isList() {
        return this instanceof ListTag1_16_5 || this.tag instanceof ListNBT;
    }

    @Override
    public boolean isString() {
        return this instanceof StringTag1_16_5 || this.tag instanceof StringNBT;
    }

    @Override
    public boolean isPrimitive() {
        return this instanceof PrimitiveTag1_16_5 || this.tag instanceof NumberNBT;
    }
}
