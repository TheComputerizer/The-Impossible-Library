package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Item1_16_5 extends ItemAPI<Item> {

    public Item1_16_5(Object item) {
        super((Item)item);
    }
    
    @Override public ItemStackAPI<?> defaultStack() {
        return WrapperHelper.wrapItemStack(new ItemStack(getWrapped()));
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        if(FORGE) this.wrapped.setRegistryName((ResourceLocation)registryName.unwrap());
    }
}