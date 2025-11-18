package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ToolBuilderAPI;
import org.jetbrains.annotations.Nullable;

public class ToolBuilder1_20_6 extends ToolBuilderAPI implements ItemBuilderHelpers1_20_6 {
    
    public ToolBuilder1_20_6(@Nullable ItemBuilderAPI builder, ToolType type) {
        super(builder,type);
    }
    
    @Override public ItemAPI<?> build() {
        return defaultBuild(buildProperties(),this::makeItem);
    }
}