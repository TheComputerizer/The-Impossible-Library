package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.Item1_12_2;

import javax.annotation.Nullable;

public class ItemBuilder1_12_2 extends ItemBuilderAPI {
    
    public ItemBuilder1_12_2(@Nullable ItemBuilderAPI parent) {
        super(parent);
    }
    
    @Override public Item1_12_2 build() {
        return new Item1_12_2(new TILBasicItem1_12_2(buildProperties()));
    }
}
