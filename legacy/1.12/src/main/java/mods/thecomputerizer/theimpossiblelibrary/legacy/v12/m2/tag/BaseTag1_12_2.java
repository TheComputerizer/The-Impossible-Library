package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import net.minecraft.nbt.*;

@Getter
public class BaseTag1_12_2<N extends NBTBase> extends BaseTagAPI<N> {

    public BaseTag1_12_2(N tag) {
        super(tag);
    }

    @Override
    public CompoundTag1_12_2 asCompoundTag() {
        if(this instanceof CompoundTag1_12_2) return (CompoundTag1_12_2)this;
        if(this.wrapped instanceof NBTTagCompound) return new CompoundTag1_12_2((NBTTagCompound)this.wrapped);
        return null;
    }

    @Override
    public ListTag1_12_2 asListTag() {
        if(this instanceof ListTag1_12_2) return (ListTag1_12_2)this;
        if(this.wrapped instanceof NBTTagList) return new ListTag1_12_2((NBTTagList)this.wrapped);
        return null;
    }

    @Override
    public PrimitiveTag1_12_2 asPrimitiveTag() {
        if(this instanceof PrimitiveTag1_12_2) return (PrimitiveTag1_12_2)this;
        if(this.wrapped instanceof NBTPrimitive) return new PrimitiveTag1_12_2((NBTPrimitive)this.wrapped);
        return null;
    }

    @Override
    public StringTag1_12_2 asStringTag() {
        if(this instanceof StringTag1_12_2) return (StringTag1_12_2)this;
        if(this.wrapped instanceof NBTTagString) return new StringTag1_12_2((NBTTagString)this.wrapped);
        return null;
    }

    @Override
    public boolean isCompound() {
        return this instanceof CompoundTag1_12_2 || this.wrapped instanceof NBTTagCompound;
    }

    @Override
    public boolean isList() {
        return this instanceof ListTag1_12_2 || this.wrapped instanceof NBTTagList;
    }

    @Override
    public boolean isString() {
        return this instanceof StringTag1_12_2 || this.wrapped instanceof NBTTagString;
    }

    @Override
    public boolean isPrimitive() {
        return this instanceof PrimitiveTag1_12_2 || this.wrapped instanceof NBTPrimitive;
    }
}
