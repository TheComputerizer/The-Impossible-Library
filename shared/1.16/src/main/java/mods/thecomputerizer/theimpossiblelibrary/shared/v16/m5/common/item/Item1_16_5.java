package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraft.item.Item;

public class Item1_16_5 extends ItemAPI<Item> {

    public Item1_16_5(Item item) {
        super(item);
    }

    @Override
    public ResourceLocation1_16_5 getRegistryName() {
        return new ResourceLocation1_16_5(this.item.getRegistryName());
    }
}