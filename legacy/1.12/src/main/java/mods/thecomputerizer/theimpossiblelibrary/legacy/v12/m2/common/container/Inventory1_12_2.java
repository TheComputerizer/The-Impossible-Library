package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.container;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.container.InventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.minecraft.inventory.IInventory;

public class Inventory1_12_2 extends InventoryAPI<IInventory> {

    public Inventory1_12_2(IInventory inventory) {
        super(inventory);
    }

    @Override public ItemStackAPI<?> getStack(int slot) {
        return WrapperHelper.wrapItemStack(this.wrapped.getStackInSlot(slot));
    }

    @Override public int getSlots() {
        return this.wrapped.getSizeInventory();
    }

    @Override public boolean isEmpty() {
        return this.wrapped.isEmpty();
    }

    @Override public void setStack(ItemStackAPI<?> stack, int slot) {
        this.wrapped.setInventorySlotContents(slot,stack.unwrap());
    }
}