package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.container;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.container.InventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.minecraft.inventory.IInventory;

import java.util.Objects;

import static net.minecraft.item.ItemStack.EMPTY;

public class Inventory1_12_2 extends InventoryAPI<IInventory> {

    public Inventory1_12_2(Object inventory) {
        super(inventory);
    }

    @Override public ItemStackAPI<?> getStack(int slot) {
        return WrapperHelper.wrapItemStack(getIfNotNullOrDefault(w -> w.getStackInSlot(slot),EMPTY));
    }

    @Override public int getSlots() {
        return getIfNotNullOrDefault(IInventory::getSizeInventory,0);
    }

    @Override public boolean isEmpty() {
        return getIfNotNullOrDefault(IInventory::isEmpty,true);
    }

    @Override public void setStack(ItemStackAPI<?> stack, int slot) {
        if(Objects.nonNull(this.wrapped)) this.wrapped.setInventorySlotContents(slot,stack.unwrap());
    }
}