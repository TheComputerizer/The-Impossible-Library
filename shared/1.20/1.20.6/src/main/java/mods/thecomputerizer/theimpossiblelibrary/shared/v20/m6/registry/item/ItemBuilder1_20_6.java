package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import org.jetbrains.annotations.Nullable;

public class ItemBuilder1_20_6 extends ItemBuilderAPI implements ItemBuilderHelpers1_20_6 {
    
    public ItemBuilder1_20_6(@Nullable ItemBuilderAPI parent) {
        super(parent);
    }
    
    @Override public ItemAPI<?> build() {
        return defaultBuild(buildProperties());
    }
}