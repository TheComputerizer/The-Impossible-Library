package mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Item1_21 extends ItemAPI<Item> {

    public Item1_21(Object item) {
        super((Item)item);
    }
    
    @Override public ItemStackAPI<?> defaultStack() {
        return WrapperHelper.wrapItemStack(new ItemStack(getWrapped()));
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName); //There is no built-in registryName field for forge in 1.19.+
    }
}