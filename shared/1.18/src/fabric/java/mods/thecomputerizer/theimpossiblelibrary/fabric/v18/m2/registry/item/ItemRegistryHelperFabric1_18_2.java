package mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.item.ItemRegistryHelper1_18_2;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemRegistryHelperFabric1_18_2 extends ItemRegistryHelper1_18_2 {
    
    @IndirectCallers
    public static ItemRegistryHelper1_18_2 getInstance() {
        return new ItemRegistryHelperFabric1_18_2();
    }
    
    private ItemRegistryHelperFabric1_18_2() {}
    
    @Override protected void registerItemModelVariant(Item item, ResourceLocation location,
            ClampedItemPropertyFunction func) {
        Hacks.invokeStaticDirect(ItemProperties.class,"register","method_27879",item,location,func);
    }
}