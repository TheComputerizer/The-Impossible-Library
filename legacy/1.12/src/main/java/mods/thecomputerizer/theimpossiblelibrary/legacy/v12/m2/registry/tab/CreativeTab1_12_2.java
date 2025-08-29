package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

import static net.minecraft.item.ItemStack.EMPTY;

public class CreativeTab1_12_2 extends CreativeTabAPI<CreativeTabs> {
    
    public CreativeTab1_12_2(Object wrapped) {
        super(wrapped);
    }
    
    @Override public void addStack(Supplier<ItemStackAPI<?>> supplier) {
        this.stacks.add(supplier);
        ItemStack stack = supplier.get().unwrap();
        stack.getItem().setCreativeTab(unwrap());
    }
    
    @Override public ItemStackAPI<?> getIcon() {
        return WrapperHelper.wrapItemStack(getIfNotNullOrDefault(CreativeTabs::getIcon,EMPTY));
    }
    
    @Override public <P> P withItemProperties(P properties) {
        return null; //Not needed in 1.12.2
    }
}
