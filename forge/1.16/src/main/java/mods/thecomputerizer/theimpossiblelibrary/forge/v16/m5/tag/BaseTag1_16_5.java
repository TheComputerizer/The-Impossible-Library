package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.tag;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import net.minecraft.nbt.*;

@Getter
public class BaseTag1_16_5<N extends INBT> extends BaseTagAPI<N> {
    
    public BaseTag1_16_5(N tag) {
        super(tag);
    }

    @Override
    public CompoundTag1_16_5 asCompoundTag() {
        if(this instanceof CompoundTag1_16_5) return (CompoundTag1_16_5)this;
        if(this.wrapped instanceof CompoundNBT) return new CompoundTag1_16_5((CompoundNBT)this.wrapped);
        return null;
    }

    @Override
    public ListTag1_16_5 asListTag() {
        if(this instanceof ListTag1_16_5) return (ListTag1_16_5)this;
        if(this.wrapped instanceof ListNBT) return new ListTag1_16_5((ListNBT)this.wrapped);
        return null;
    }

    @Override
    public PrimitiveTag1_16_5 asPrimitiveTag() {
        if(this instanceof PrimitiveTag1_16_5) return (PrimitiveTag1_16_5)this;
        if(this.wrapped instanceof NumberNBT) return new PrimitiveTag1_16_5((NumberNBT)this.wrapped);
        return null;
    }

    @Override
    public StringTag1_16_5 asStringTag() {
        if(this instanceof StringTag1_16_5) return (StringTag1_16_5)this;
        if(this.wrapped instanceof StringNBT) return new StringTag1_16_5((StringNBT)this.wrapped);
        return null;
    }

    @Override
    public boolean isCompound() {
        return this instanceof CompoundTag1_16_5 || this.wrapped instanceof CompoundNBT;
    }

    @Override
    public boolean isList() {
        return this instanceof ListTag1_16_5 || this.wrapped instanceof ListNBT;
    }

    @Override
    public boolean isString() {
        return this instanceof StringTag1_16_5 || this.wrapped instanceof StringNBT;
    }

    @Override
    public boolean isPrimitive() {
        return this instanceof PrimitiveTag1_16_5 || this.wrapped instanceof NumberNBT;
    }
}
