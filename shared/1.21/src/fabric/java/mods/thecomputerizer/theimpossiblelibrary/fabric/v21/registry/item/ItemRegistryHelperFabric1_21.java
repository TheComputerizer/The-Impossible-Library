package mods.thecomputerizer.theimpossiblelibrary.fabric.v21.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.item.ItemRegistryHelper1_21;
import net.fabricmc.fabric.mixin.object.builder.client.ModelPredicateProviderRegistrySpecificAccessor;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemRegistryHelperFabric1_21 extends ItemRegistryHelper1_21 {
    
    @IndirectCallers
    public static ItemRegistryHelper1_21 getInstance() {
        return new ItemRegistryHelperFabric1_21();
    }
    
    private ItemRegistryHelperFabric1_21() {
    
    }
    
    @Override protected void registerItemModelVariant(Item item, ResourceLocation location,
            ClampedItemPropertyFunction func) {
        ModelPredicateProviderRegistrySpecificAccessor.callRegister(item,location,func);
        ItemProperties.register(item, location, func);
    }
}