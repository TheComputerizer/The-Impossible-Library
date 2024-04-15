package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.container;

import mods.thecomputerizer.theimpossiblelibrary.api.common.container.PlayerInventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.ItemStack1_12_2;
import net.minecraft.entity.player.InventoryPlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerInventory1_12_2 extends PlayerInventoryAPI<InventoryPlayer> {

    public PlayerInventory1_12_2(InventoryPlayer inventory) {
        super(inventory);
    }

    @Override
    public Collection<ItemStackAPI<?>> getArmorStacks() {
        return this.inventory.armorInventory.stream().map(ItemStack1_12_2::new).collect(Collectors.toList());
    }

    @Override
    public Collection<ItemStackAPI<?>> getHotbarStacks() {
        List<ItemStackAPI<?>> stacks = new ArrayList<>();
        for(int i=0;i<9;i++) {
            ItemStackAPI<?> stack = getStack(i);
            if(stack.isNotEmpty()) stacks.add(stack);
        }
        return stacks;
    }

    @Override
    public Collection<ItemStackAPI<?>> getMainStacks() {
        return this.inventory.mainInventory.stream().map(ItemStack1_12_2::new).collect(Collectors.toList());
    }

    @Override
    public Collection<ItemStackAPI<?>> getOffHandStacks() {
        return this.inventory.offHandInventory.stream().map(ItemStack1_12_2::new).collect(Collectors.toList());
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
