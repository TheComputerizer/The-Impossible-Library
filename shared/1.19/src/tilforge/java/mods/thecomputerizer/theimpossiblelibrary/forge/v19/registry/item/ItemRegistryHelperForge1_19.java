package mods.thecomputerizer.theimpossiblelibrary.forge.v19.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.registry.item.ItemRegistryHelper1_19;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemRegistryHelperForge1_19 extends ItemRegistryHelper1_19 {
    
    @IndirectCallers
    public static ItemRegistryHelper1_19 getInstance() {
        return new ItemRegistryHelperForge1_19();
    }
    
    private ItemRegistryHelperForge1_19() {}
    
    @Override protected void registerItemModelVariant(Item item, ResourceLocation location,
            ClampedItemPropertyFunction func) {
        ItemProperties.register(item,location,func);
    }
}