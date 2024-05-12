package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ToolBuilderAPI;
import net.minecraft.item.Item.Properties;

import javax.annotation.Nullable;

public class ToolBulder1_16_5 extends ToolBuilderAPI {
    
    public ToolBulder1_16_5(@Nullable ItemBuilderAPI builder, ToolType type) {
        super(builder,type);
    }
    
    @Override public ItemAPI<?> build() { //TODO
        ItemProperties otherProperties = buildProperties();
        Properties properties = new Properties().stacksTo(otherProperties.getStackSize());
        return null;
    }
}
