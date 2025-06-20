package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.item.ItemRegistryHelper1_18_2;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemRegistryHelperForge1_18_2 extends ItemRegistryHelper1_18_2 {
    
    @IndirectCallers
    public static ItemRegistryHelper1_18_2 getInstance() {
        return new ItemRegistryHelperForge1_18_2();
    }
    
    private ItemRegistryHelperForge1_18_2() {}
    
    @Override protected void registerItemModelVariant(Item item, ResourceLocation location,
            ClampedItemPropertyFunction func) {
        ItemProperties.register(item,location,func);
    }
}