package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.container;

import mods.thecomputerizer.theimpossiblelibrary.api.common.container.InventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.ItemStack1_12_2;
import net.minecraft.inventory.IInventory;

public class Inventory1_12_2 extends InventoryAPI<IInventory> {

    public Inventory1_12_2(IInventory inventory) {
        super(inventory);
    }

    @Override
    public ItemStackAPI<?> getStack(int slot) {
        return new ItemStack1_12_2(this.inventory.getStackInSlot(slot));
    }

    @Override
    public int getSlots() {
        return this.inventory.getSizeInventory();
    }

    @Override
    public boolean isEmpty() {
        return this.inventory.isEmpty();
    }

    @Override
    public void setStack(ItemStackAPI<?> stack, int slot) {
        this.inventory.setInventorySlotContents(slot,((ItemStack1_12_2)stack).getStack());
    }
}
