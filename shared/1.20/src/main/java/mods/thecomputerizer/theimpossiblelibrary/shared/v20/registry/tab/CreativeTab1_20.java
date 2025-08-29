package mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Supplier;

import static net.minecraft.world.item.ItemStack.EMPTY;

public class CreativeTab1_20 extends CreativeTabAPI<CreativeModeTab> {
    
    public CreativeTab1_20(Object wrapped) {
        super(wrapped);
    }
    
    @Override public void addStack(Supplier<ItemStackAPI<?>> supplier) {} //Not valid for 1.16.5+
    
    @Override public ItemStackAPI<?> getIcon() {
        return WrapperHelper.wrapItemStack(getIfNotNullOrDefault(CreativeModeTab::getIconItem,EMPTY));
    }
    
    @Override public <P> P withItemProperties(P properties) {
        return properties; //Not valid for 1.19.4+
    }
}
