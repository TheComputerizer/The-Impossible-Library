package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.container;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.container.PlayerInventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static net.minecraft.item.ItemStack.EMPTY;

public class PlayerInventory1_12_2 extends PlayerInventoryAPI<InventoryPlayer> {

    public PlayerInventory1_12_2(Object inventory) {
        super(inventory);
    }

    @Override public Collection<ItemStackAPI<?>> getArmorStacks() {
        return getStacks(w -> w.armorInventory);
    }

    @Override public Collection<ItemStackAPI<?>> getHotbarStacks() {
        List<ItemStackAPI<?>> stacks = new ArrayList<>();
        for(int i=0;i<9;i++) {
            ItemStackAPI<?> stack = getStack(i);
            if(stack.isNotEmpty()) stacks.add(stack);
        }
        return stacks;
    }

    @Override public Collection<ItemStackAPI<?>> getMainStacks() {
        return getStacks(w -> w.mainInventory);
    }

    @Override public Collection<ItemStackAPI<?>> getOffHandStacks() {
        return getStacks(w -> w.offHandInventory);
    }
    
    @Override public ItemStackAPI<?> getStack(int slot) {
        return WrapperHelper.wrapItemStack(getIfNotNullOrDefault(w -> w.getStackInSlot(slot),EMPTY));
    }
    
    @Override public int getSlots() {
        return getIfNotNullOrDefault(IInventory::getSizeInventory, 0);
    }
    
    @Override public boolean isEmpty() {
        return getIfNotNullOrDefault(IInventory::isEmpty,true);
    }
    
    @Override public void setStack(ItemStackAPI<?> stack, int slot) {
        if(Objects.nonNull(this.wrapped))
            this.wrapped.setInventorySlotContents(slot,stack.unwrap());
    }
}