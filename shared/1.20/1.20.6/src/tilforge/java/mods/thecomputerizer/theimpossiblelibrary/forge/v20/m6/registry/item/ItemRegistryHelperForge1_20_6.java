package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.registry.item.ItemRegistryHelper1_20_6;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemRegistryHelperForge1_20_6 extends ItemRegistryHelper1_20_6 {
    
    @IndirectCallers
    public static ItemRegistryHelper1_20_6 getInstance() {
        return new ItemRegistryHelperForge1_20_6();
    }
    
    private ItemRegistryHelperForge1_20_6() {}
    
    @Override protected void registerItemModelVariant(Item item, ResourceLocation location,
            ClampedItemPropertyFunction func) {
        ItemProperties.register(item,location,func);
    }
}