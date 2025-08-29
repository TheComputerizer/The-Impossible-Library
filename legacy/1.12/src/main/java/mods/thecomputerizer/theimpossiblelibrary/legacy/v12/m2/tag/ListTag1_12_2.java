package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListTag1_12_2 extends ListTagAPI<NBTTagList> {

    public ListTag1_12_2(Object tag) {
        super(tag);
    }
    
    @Override public void addTag(BaseTagAPI<?> tag) {
        if(Objects.nonNull(this.wrapped)) this.wrapped.appendTag((NBTBase)tag.getWrapped());
    }
    
    @Override public Iterable<BaseTagAPI<?>> iterable() {
        List<BaseTagAPI<?>> tags = new ArrayList<>();
        if(Objects.nonNull(this.wrapped)) this.wrapped.forEach(based -> tags.add(TagHelper.getWrapped(based)));
        return tags;
    }
}
