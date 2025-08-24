package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.registry.item.ItemRegistryHelper1_19;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemRegistryHelperFabric1_19 extends ItemRegistryHelper1_19 {
    
    @IndirectCallers
    public static ItemRegistryHelper1_19 getInstance() {
        return new ItemRegistryHelperFabric1_19();
    }
    
    private ItemRegistryHelperFabric1_19() {}
    
    @Override protected void registerItemModelVariant(Item item, ResourceLocation location,
            ClampedItemPropertyFunction func) {
        Hacks.invokeStaticDirectNamed(ItemProperties.class,"register","method_27879",item,location,func);
    }
}