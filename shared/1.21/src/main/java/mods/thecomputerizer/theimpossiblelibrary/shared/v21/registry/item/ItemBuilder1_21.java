package mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import org.jetbrains.annotations.Nullable;

public class ItemBuilder1_21 extends ItemBuilderAPI implements ItemBuilderHelpers1_21  {
    
    public ItemBuilder1_21(@Nullable ItemBuilderAPI parent) {
        super(parent);
    }
    
    @Override public ItemAPI<?> build() {
        return defaultBuild(buildProperties());
    }
}