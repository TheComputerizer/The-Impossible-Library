package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import net.minecraft.nbt.*;

@Getter
public class BaseTag1_12_2<N extends NBTBase> implements BaseTagAPI {

    protected final N tag;

    protected BaseTag1_12_2(N tag) {
        this.tag = tag;
    }

    @Override
    public CompoundTagAPI asCompoundTag() {
        if(this instanceof CompoundTagAPI) return (CompoundTagAPI)this;
        if(this.tag instanceof NBTTagCompound) return new CompoundTag1_12_2((NBTTagCompound)this.tag);
        return null;
    }

    @Override
    public ListTagAPI asListTag() {
        if(this instanceof ListTagAPI) return (ListTagAPI)this;
        if(this.tag instanceof NBTTagList) return new ListTag1_12_2((NBTTagList)this.tag);
        return null;
    }

    @Override
    public PrimitiveTag1_12_2 asPrimitiveTag() {
        if(this instanceof PrimitiveTag1_12_2) return (PrimitiveTag1_12_2)this;
        if(this.tag instanceof NBTPrimitive) return new PrimitiveTag1_12_2((NBTPrimitive)this.tag);
        return null;
    }

    @Override
    public StringTag1_12_2 asStringTag() {
        if(this instanceof StringTag1_12_2) return (StringTag1_12_2)this;
        if(this.tag instanceof NBTTagString) return new StringTag1_12_2((NBTTagString)this.tag);
        return null;
    }

    @Override
    public boolean isCompound() {
        return this instanceof CompoundTag1_12_2 || this.tag instanceof NBTTagCompound;
    }

    @Override
    public boolean isList() {
        return this instanceof ListTag1_12_2 || this.tag instanceof NBTTagList;
    }

    @Override
    public boolean isString() {
        return this instanceof StringTag1_12_2 || this.tag instanceof NBTTagString;
    }

    @Override
    public boolean isPrimitive() {
        return this instanceof PrimitiveTag1_12_2 || this.tag instanceof NBTPrimitive;
    }
}
