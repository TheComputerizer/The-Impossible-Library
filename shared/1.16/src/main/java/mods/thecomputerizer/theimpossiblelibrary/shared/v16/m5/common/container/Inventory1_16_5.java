package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.container;

import mods.thecomputerizer.theimpossiblelibrary.api.common.container.InventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.item.ItemStack1_16_5;
import net.minecraft.inventory.IInventory;

public class Inventory1_16_5 extends InventoryAPI<IInventory> {

    public Inventory1_16_5(IInventory inventory) {
        super(inventory);
    }

    @Override
    public ItemStackAPI<?> getStack(int slot) {
        return new ItemStack1_16_5(this.inventory.getItem(slot));
    }

    @Override
    public int getSlots() {
        return this.inventory.getContainerSize();
    }

    @Override
    public boolean isEmpty() {
        return this.inventory.isEmpty();
    }

    @Override
    public void setStack(ItemStackAPI<?> stack, int slot) {
        this.inventory.setItem(slot,((ItemStack1_16_5)stack).getStack());
    }
}
