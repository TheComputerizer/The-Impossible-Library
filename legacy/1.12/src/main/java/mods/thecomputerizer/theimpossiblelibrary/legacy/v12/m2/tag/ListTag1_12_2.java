package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag;

import mcp.MethodsReturnNonnullByDefault;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import java.util.ArrayList;
import java.util.List;

@MethodsReturnNonnullByDefault
public class ListTag1_12_2 extends BaseTag1_12_2<NBTTagList> implements ListTagAPI<NBTTagList> {

    public ListTag1_12_2(NBTTagList tag) {
        super(tag);
    }

    @Override
    public void addTag(BaseTagAPI<?> tag) {
        this.wrapped.appendTag((NBTBase)tag.getWrapped());
    }
    
    @Override public void addTag(CompoundTagAPI<?> tag) {
        this.wrapped.appendTag((NBTTagCompound)tag.getWrapped());
    }
    
    @Override public void addTag(ListTagAPI<?> tag) {
        this.wrapped.appendTag((NBTTagList)tag.getWrapped());
    }
    
    @Override public void addTag(PrimitiveTagAPI<?> tag) {
        this.wrapped.appendTag((NBTPrimitive)tag.getWrapped());
    }
    
    @Override public void addTag(StringTagAPI<?> tag) {
        this.wrapped.appendTag((NBTTagString)tag.getWrapped());
    }
    
    @Override
    public Iterable<BaseTagAPI<?>> iterable() {
        List<BaseTagAPI<?>> tags = new ArrayList<>();
        this.wrapped.forEach(based -> tags.add(new BaseTag1_12_2<>(based)));
        return tags;
    }
}
