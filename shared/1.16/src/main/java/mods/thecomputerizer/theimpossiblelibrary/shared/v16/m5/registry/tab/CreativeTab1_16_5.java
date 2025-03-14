package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;

public class CreativeTab1_16_5 extends CreativeTabAPI<ItemGroup> {
    
    public CreativeTab1_16_5(Object wrapped) {
        super((ItemGroup)wrapped);
    }
    
    @Override public void addStack(ItemStackAPI<?> stack) {} //Not valid for 1.16.5-1.19.2
    
    @Override public ItemStackAPI<?> getIcon() {
        return WrapperHelper.wrapItemStack(this.wrapped.getIconItem());
    }
    
    @SuppressWarnings("unchecked")
    @Override public <P> P withItemProperties(P properties) {
        return (P)((Properties)properties).tab(this.wrapped);
    }
}
