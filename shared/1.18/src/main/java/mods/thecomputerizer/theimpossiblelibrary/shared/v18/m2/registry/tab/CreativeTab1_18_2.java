package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item.Properties;

import java.util.function.Supplier;

import static net.minecraft.world.item.ItemStack.EMPTY;

public class CreativeTab1_18_2 extends CreativeTabAPI<CreativeModeTab> {
    
    public CreativeTab1_18_2(Object wrapped) {
        super(wrapped);
    }
    
    @Override public void addStack(Supplier<ItemStackAPI<?>> supplier) {} //Not valid for 1.16.5-1.19.2
    
    @Override public ItemStackAPI<?> getIcon() {
        return WrapperHelper.wrapItemStack(getIfNotNullOrDefault(CreativeModeTab::getIconItem,EMPTY));
    }
    
    @Override public <P> P withItemProperties(P properties) {
        return GenericUtils.cast(((Properties)properties).tab(this.wrapped));
    }
}
