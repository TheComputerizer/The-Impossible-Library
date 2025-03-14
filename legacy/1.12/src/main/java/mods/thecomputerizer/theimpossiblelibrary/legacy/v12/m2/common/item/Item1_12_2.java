package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Item1_12_2 extends ItemAPI<Item> {

    public Item1_12_2(Object item) {
        super((Item)item);
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        this.wrapped.setRegistryName((ResourceLocation)registryName.unwrap());
    }
    
    @Override public ItemStackAPI<?> defaultStack() {
        return WrapperHelper.wrapItemStack(new ItemStack(getWrapped()));
    }
}