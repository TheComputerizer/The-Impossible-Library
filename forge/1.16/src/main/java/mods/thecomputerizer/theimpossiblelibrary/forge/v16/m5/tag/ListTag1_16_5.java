package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NumberNBT;
import net.minecraft.nbt.StringNBT;

import java.util.ArrayList;
import java.util.List;

public class ListTag1_16_5 extends BaseTag1_16_5<ListNBT> implements ListTagAPI<ListNBT> {

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
    public Iterable<BaseTagAPI<?>> iterable() {
        List<BaseTagAPI<?>> tags = new ArrayList<>();
        this.wrapped.forEach(based -> tags.add(new BaseTag1_16_5<>(based)));
        return tags;
    }
}
