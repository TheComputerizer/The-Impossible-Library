package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NumberNBT;
import net.minecraft.nbt.StringNBT;

import java.util.ArrayList;
import java.util.List;

public class ListTag1_16_5 extends ListTagAPI<ListNBT> {

    public ListTag1_16_5(ListNBT tag) {
        super(tag);
    }

    @Override
    public void addTag(BaseTagAPI<?> tag) {
        this.wrapped.add((INBT)tag.getWrapped());
    }
    
    @Override public void addTag(CompoundTagAPI<?> tag) {
        this.wrapped.add((CompoundNBT)tag.getWrapped());
    }
    
    @Override public void addTag(ListTagAPI<?> tag) {
        this.wrapped.add((ListNBT)tag.getWrapped());
    }
    
    @Override public void addTag(PrimitiveTagAPI<?> tag) {
        this.wrapped.add((NumberNBT)tag.getWrapped());
    }
    
    @Override public void addTag(StringTagAPI<?> tag) {
        this.wrapped.add((StringNBT)tag.getWrapped());
    }
    
    @Override
    public CompoundTag1_16_5 asCompoundTag() {
        return null;
    }
    
    @Override
    public ListTag1_16_5 asListTag() {
        return this;
    }
    
    @Override
    public PrimitiveTag1_16_5 asPrimitiveTag() {
        return null;
    }
    
    @Override
    public StringTag1_16_5 asStringTag() {
        return null;
    }
    
    @Override
    public boolean isCompound() {
        return false;
    }
    
    @Override
    public boolean isList() {
        return true;
    }
    
    @Override
    public boolean isPrimitive() {
        return false;
    }
    
    @Override
    public boolean isString() {
        return false;
    }
    
    @Override
    public Iterable<BaseTagAPI<?>> iterable() {
        List<BaseTagAPI<?>> tags = new ArrayList<>();
        this.wrapped.forEach(based -> tags.add(TagHelper.getWrapped(based)));
        return tags;
    }
}
