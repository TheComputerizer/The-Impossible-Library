package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ToolBuilderAPI;
import org.jetbrains.annotations.Nullable;

public class ToolBuilder1_16_5 extends ToolBuilderAPI implements ItemBuilderHelpers1_16_5 {
    
    public ToolBuilder1_16_5(@Nullable ItemBuilderAPI builder, ToolType type) {
        super(builder,type);
    }
    
    @Override public ItemAPI<?> build() {
        return defaultBuild(buildProperties(),this::makeItem);
    }
}