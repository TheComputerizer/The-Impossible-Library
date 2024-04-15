package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.container;

import mods.thecomputerizer.theimpossiblelibrary.api.common.container.PlayerInventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.item.ItemStack1_16_5;
import net.minecraft.entity.player.PlayerInventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerInventory1_16_5 extends PlayerInventoryAPI<PlayerInventory> {

    public PlayerInventory1_16_5(PlayerInventory inventory) {
        super(inventory);
    }

    @Override
    public Collection<ItemStackAPI<?>> getArmorStacks() {
        return this.inventory.armor.stream().map(ItemStack1_16_5::new).collect(Collectors.toList());
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
        return this.inventory.items.stream().map(ItemStack1_16_5::new).collect(Collectors.toList());
    }

    @Override
    public Collection<ItemStackAPI<?>> getOffHandStacks() {
        return this.inventory.offhand.stream().map(ItemStack1_16_5::new).collect(Collectors.toList());
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
