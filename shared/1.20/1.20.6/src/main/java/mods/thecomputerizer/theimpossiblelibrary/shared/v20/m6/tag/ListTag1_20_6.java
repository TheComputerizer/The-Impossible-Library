package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.tag.ListTag1_20;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListTag1_20_6 extends ListTag1_20 implements TagWrapper {

    public ListTag1_20_6(Object tag) {
        super(tag);
    }

    @Override public void addTag(BaseTagAPI<?> tag) {
        if(Objects.nonNull(this.wrapped)) this.wrapped.add(tag.unwrap());
    }
    
    @Override public Iterable<BaseTagAPI<?>> iterable() {
        List<BaseTagAPI<?>> tags = new ArrayList<>();
        if(Objects.nonNull(this.wrapped)) this.wrapped.forEach(based -> tags.add(TagHelper.getWrapped(based)));
        return tags;
    }
}