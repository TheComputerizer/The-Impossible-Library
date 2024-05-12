package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.item.Item1_16_5;
import net.minecraft.item.Item.Properties;

import javax.annotation.Nullable;

public class ItemBuilder1_16_5 extends ItemBuilderAPI {
    
    public ItemBuilder1_16_5(@Nullable ItemBuilderAPI parent) {
        super(parent);
    }
    
    @Override public Item1_16_5 build() {
        ItemProperties otherProperties = buildProperties();
        Properties properties = new Properties().stacksTo(otherProperties.getStackSize());
        return new Item1_16_5(new TILBasicItem1_16_5(properties,otherProperties));
    }
}
