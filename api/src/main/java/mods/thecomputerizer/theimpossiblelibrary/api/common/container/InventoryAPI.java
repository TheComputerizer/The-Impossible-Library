package mods.thecomputerizer.theimpossiblelibrary.api.common.container;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.util.AbstractWrapped;

public abstract class InventoryAPI<I> extends AbstractWrapped<I> {

    protected InventoryAPI(I inventory) {
        super(inventory);
    }

    public abstract ItemStackAPI<?> getStack(int slot);
    public abstract int getSlots();

    @IndirectCallers
    public boolean hasItem(ItemAPI<?> item) {
        for(int i=0;i<getSlots();i++) {
            ItemStackAPI<?> slot = getStack(i);
            if(slot.isNotEmpty() && slot.getItem().getWrapped()==item.getWrapped()) return true;
        }
        return false;
    }
    
    @IndirectCallers
    public boolean hasStack(ItemStackAPI<?> stack) {
        for(int i=0;i<getSlots();i++)
            if(getStack(i).getWrapped()==stack.getWrapped()) return true;
        return false;
    }

    public abstract boolean isEmpty();

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public boolean isSlotEmpty(int slot) {
        return getStack(slot).isEmpty();
    }
    
    @IndirectCallers
    public boolean isSlotNotEmpty(int slot) {
        return !isSlotEmpty(slot);
    }
    
    @IndirectCallers
    public abstract void setStack(ItemStackAPI<?> stack, int slot);
}