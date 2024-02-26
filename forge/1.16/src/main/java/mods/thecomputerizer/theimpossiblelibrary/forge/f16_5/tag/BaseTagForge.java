package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.tag;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import net.minecraft.nbt.*;

@Getter
public class BaseTagForge<N extends INBT> implements BaseTagAPI {

    protected final N tag;

    protected BaseTagForge(N tag) {
        this.tag = tag;
    }

    @Override
    public CompoundTagAPI asCompoundTag() {
        if(this instanceof CompoundTagAPI) return (CompoundTagAPI)this;
        if(this.tag instanceof CompoundNBT) return new CompoundTagForge((CompoundNBT)this.tag);
        return null;
    }

    @Override
    public ListTagAPI asListTag() {
        if(this instanceof ListTagAPI) return (ListTagAPI)this;
        if(this.tag instanceof ListNBT) return new ListTagForge((ListNBT)this.tag);
        return null;
    }

    @Override
    public PrimitiveTagForge asPrimitiveTag() {
        if(this instanceof PrimitiveTagForge) return (PrimitiveTagForge)this;
        if(this.tag instanceof NumberNBT) return new PrimitiveTagForge((NumberNBT)this.tag);
        return null;
    }

    @Override
    public StringTagForge asStringTag() {
        if(this instanceof StringTagForge) return (StringTagForge)this;
        if(this.tag instanceof StringNBT) return new StringTagForge((StringNBT)this.tag);
        return null;
    }

    @Override
    public boolean isCompound() {
        return this instanceof CompoundTagForge || this.tag instanceof CompoundNBT;
    }

    @Override
    public boolean isList() {
        return this instanceof ListTagForge || this.tag instanceof ListNBT;
    }

    @Override
    public boolean isString() {
        return this instanceof StringTagForge || this.tag instanceof StringNBT;
    }

    @Override
    public boolean isPrimitive() {
        return this instanceof PrimitiveTagForge || this.tag instanceof NumberNBT;
    }
}
