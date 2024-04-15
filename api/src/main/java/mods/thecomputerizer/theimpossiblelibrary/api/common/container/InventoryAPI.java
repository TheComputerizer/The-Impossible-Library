package mods.thecomputerizer.theimpossiblelibrary.api.common.container;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;

@Getter
public abstract class InventoryAPI<I> {

    protected final I inventory;

    protected InventoryAPI(I inventory) {
        this.inventory = inventory;
    }

    public abstract ItemStackAPI<?> getStack(int slot);
    public abstract int getSlots();

    public boolean hasItem(ItemAPI<?> item) {
        for(int i=0;i<getSlots();i++) {
            ItemStackAPI<?> slot = getStack(i);
            if(slot.isNotEmpty() && slot.getItem().getItem()==item.getItem()) return true;
        }
        return false;
    }

    public boolean hasStack(ItemStackAPI<?> stack) {
        for(int i=0;i<getSlots();i++)
            if(getStack(i).getStack()==stack.getStack()) return true;
        return false;
    }

    public abstract boolean isEmpty();


    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public boolean isSlotEmpty(int slot) {
        return getStack(slot).isEmpty();
    }

    public boolean isSlotNotEmpty(int slot) {
        return !isSlotEmpty(slot);
    }

    public abstract void setStack(ItemStackAPI<?> stack, int slot);
}