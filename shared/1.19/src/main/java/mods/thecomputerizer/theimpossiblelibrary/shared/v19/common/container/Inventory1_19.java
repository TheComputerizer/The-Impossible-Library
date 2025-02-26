package mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.container;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.container.InventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.minecraft.world.Container;

public class Inventory1_19 extends InventoryAPI<Container> {

    public Inventory1_19(Object inventory) {
        super((Container)inventory);
    }

    @Override public ItemStackAPI<?> getStack(int slot) {
        return WrapperHelper.wrapItemStack(this.wrapped.getItem(slot));
    }

    @Override public int getSlots() {
        return this.wrapped.getContainerSize();
    }

    @Override public boolean isEmpty() {
        return this.wrapped.isEmpty();
    }

    @Override public void setStack(ItemStackAPI<?> stack, int slot) {
        this.wrapped.setItem(slot,stack.unwrap());
    }
}