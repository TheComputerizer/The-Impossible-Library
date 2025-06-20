package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.item.ItemRegistryHelper1_16_5;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemRegistryHelperForge1_16_5 extends ItemRegistryHelper1_16_5 {
    
    @IndirectCallers
    public static ItemRegistryHelper1_16_5 getInstance() {
        return new ItemRegistryHelperForge1_16_5();
    }
    
    private ItemRegistryHelperForge1_16_5() {}
    
    @Override protected void registerItemModelVariant(Item item, ResourceLocation location,
            ItemPropertyFunction func) {
        ItemProperties.register(item,location,func);
    }
}