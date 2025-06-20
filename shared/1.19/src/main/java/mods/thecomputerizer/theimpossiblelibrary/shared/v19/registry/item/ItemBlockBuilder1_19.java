package mods.thecomputerizer.theimpossiblelibrary.shared.v19.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBlockBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import org.jetbrains.annotations.Nullable;

public class ItemBlockBuilder1_19 extends ItemBlockBuilderAPI implements ItemBuilderHelpers1_19 {
    
    public ItemBlockBuilder1_19(@Nullable ItemBuilderAPI parent) {
        super(parent);
    }
    
    @Override public ItemAPI<?> build() {
        return defaultBuild(buildProperties(),this::makeItem);
    }
    
    @Override public ResourceLocationAPI<?> getRegistryNameField() {
        return getRegistryName();
    }
}