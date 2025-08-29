package mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.container;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.container.InventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.minecraft.world.Container;

import java.util.Objects;

import static net.minecraft.world.item.ItemStack.EMPTY;

public class Inventory1_21 extends InventoryAPI<Container> {

    public Inventory1_21(Object inventory) {
        super(inventory);
    }
    
    @Override public ItemStackAPI<?> getStack(int slot) {
        return WrapperHelper.wrapItemStack(getIfNotNullOrDefault(w -> w.getItem(slot),EMPTY));
    }
    
    @Override public int getSlots() {
        return getIfNotNullOrDefault(Container::getContainerSize, 0);
    }
    
    @Override public boolean isEmpty() {
        return getIfNotNullOrDefault(Container::isEmpty,true);
    }
    
    @Override public void setStack(ItemStackAPI<?> stack, int slot) {
        if(Objects.nonNull(this.wrapped)) this.wrapped.setItem(slot, stack.unwrap());
    }
}