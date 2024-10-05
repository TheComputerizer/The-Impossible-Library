package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.container;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.container.PlayerInventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.ItemStack1_12_2;
import net.minecraft.entity.player.InventoryPlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerInventory1_12_2 extends PlayerInventoryAPI<InventoryPlayer> {

    public PlayerInventory1_12_2(Object inventory) {
        super((InventoryPlayer)inventory);
    }

    @Override public Collection<ItemStackAPI<?>> getArmorStacks() {
        return this.wrapped.armorInventory.stream().map(WrapperHelper::wrapItemStack).collect(Collectors.toList());
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
        return this.wrapped.mainInventory.stream().map(WrapperHelper::wrapItemStack).collect(Collectors.toList());
    }

    @Override public Collection<ItemStackAPI<?>> getOffHandStacks() {
        return this.wrapped.offHandInventory.stream().map(WrapperHelper::wrapItemStack).collect(Collectors.toList());
    }

    @Override public ItemStackAPI<?> getStack(int slot) {
        return new ItemStack1_12_2(this.wrapped.getStackInSlot(slot));
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