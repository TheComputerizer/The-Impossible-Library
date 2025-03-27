package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Item1_18_2 extends ItemAPI<Item> {

    public Item1_18_2(Object item) {
        super(item instanceof Holder<?> ? (Item)((Holder<?>)item).value() : (Item)item);
    }
    
    @Override public ItemStackAPI<?> defaultStack() {
        return WrapperHelper.wrapItemStack(new ItemStack(getWrapped()));
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        if(FORGE) this.wrapped.setRegistryName((ResourceLocation)registryName.unwrap());
    }
}