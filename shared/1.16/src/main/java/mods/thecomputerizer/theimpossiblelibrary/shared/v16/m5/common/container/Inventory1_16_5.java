package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.container;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.container.InventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.minecraft.inventory.IInventory;

public class Inventory1_16_5 extends InventoryAPI<IInventory> {

    public Inventory1_16_5(IInventory inventory) {
        super(inventory);
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