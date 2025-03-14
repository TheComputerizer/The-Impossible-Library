package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTab1_12_2 extends CreativeTabAPI<CreativeTabs> {
    
    public CreativeTab1_12_2(Object wrapped) {
        super((CreativeTabs)wrapped);
    }
    
    @Override public void addStack(ItemStackAPI<?> api) {
        this.stacks.add(api);
        ((ItemStack)api.unwrap()).getItem().setCreativeTab(getWrapped());
    }
    
    @Override public ItemStackAPI<?> getIcon() {
        return WrapperHelper.wrapItemStack(this.wrapped.getIcon());
    }
    
    @Override public <P> P withItemProperties(P properties) {
        return null; //Not needed in 1.12.2
    }
}
