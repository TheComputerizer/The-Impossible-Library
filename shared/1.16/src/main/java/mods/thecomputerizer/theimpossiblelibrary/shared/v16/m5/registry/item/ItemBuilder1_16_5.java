package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import org.jetbrains.annotations.Nullable;

public class ItemBuilder1_16_5 extends ItemBuilderAPI implements ItemBuilderHelpers1_16_5 {
    
    public ItemBuilder1_16_5(@Nullable ItemBuilderAPI parent) {
        super(parent);
    }
    
    @Override public ItemAPI<?> build() {
        return defaultBuild(buildProperties());
    }
}