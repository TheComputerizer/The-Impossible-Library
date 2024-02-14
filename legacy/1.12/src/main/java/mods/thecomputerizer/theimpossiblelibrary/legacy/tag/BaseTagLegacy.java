package mods.thecomputerizer.theimpossiblelibrary.legacy.tag;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import net.minecraft.nbt.*;

@Getter
public abstract class BaseTagLegacy<N extends NBTBase> implements BaseTagAPI {

    protected final N tag;

    protected BaseTagLegacy(N tag) {
        this.tag = tag;
    }

    @Override
    public CompoundTagAPI asCompoundTag() {
        if(this instanceof CompoundTagAPI) return (CompoundTagAPI)this;
        if(this.tag instanceof NBTTagCompound) return new CompoundTagLegacy((NBTTagCompound)this.tag);
        return null;
    }

    @Override
    public ListTagAPI asListTag() {
        if(this instanceof ListTagAPI) return (ListTagAPI)this;
        if(this.tag instanceof NBTTagList) return new ListTagLegacy((NBTTagList)this.tag);
        return null;
    }

    @Override
    public boolean isCompound() {
        return this instanceof CompoundTagLegacy || this.tag instanceof NBTTagCompound;
    }

    @Override
    public boolean isList() {
        return this instanceof ListTagLegacy || this.tag instanceof NBTTagList;
    }

    @Override
    public boolean isString() {
        return this instanceof StringTagLegacy || this.tag instanceof NBTTagString;
    }

    @Override
    public boolean isPrimitive() {
        return this instanceof PrimitiveTagLegacy || this.tag instanceof NBTPrimitive;
    }
}
