package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item.Properties;

import java.util.function.Supplier;

public class CreativeTab1_16_5 extends CreativeTabAPI<CreativeModeTab> {
    
    public CreativeTab1_16_5(Object wrapped) {
        super((CreativeModeTab)wrapped);
    }
    
    @Override public void addStack(Supplier<ItemStackAPI<?>> supplier) {} //Not valid for 1.16.5-1.19.2
    
    @Override public ItemStackAPI<?> getIcon() {
        return WrapperHelper.wrapItemStack(this.wrapped.getIconItem());
    }
    
    @SuppressWarnings("unchecked")
    @Override public <P> P withItemProperties(P properties) {
        return (P)((Properties)properties).tab(this.wrapped);
    }
}
