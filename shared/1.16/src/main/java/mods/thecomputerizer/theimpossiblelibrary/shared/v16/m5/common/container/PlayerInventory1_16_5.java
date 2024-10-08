package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.container;

import mods.thecomputerizer.theimpossiblelibrary.api.common.container.PlayerInventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.entity.player.PlayerInventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerInventory1_16_5 extends PlayerInventoryAPI<PlayerInventory> {
    
    public PlayerInventory1_16_5(Object inventory) {
        super((PlayerInventory)inventory);
    }
    
    @Override public Collection<ItemStackAPI<?>> getArmorStacks() {
        return this.wrapped.armor.stream().map(WrapperHelper::wrapItemStack).collect(Collectors.toList());
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
        return this.wrapped.items.stream().map(WrapperHelper::wrapItemStack).collect(Collectors.toList());
    }
    
    @Override public Collection<ItemStackAPI<?>> getOffHandStacks() {
        return this.wrapped.offhand.stream().map(WrapperHelper::wrapItemStack).collect(Collectors.toList());
    }
    
    @Override public ItemStackAPI<?> getStack(int slot) {
        return WrapperHelper.wrapItemStack(this.wrapped.getItem(slot));
    }
    
    @Override public int getSlots() {
        return this.wrapped.getContainerSize();
    }
    
    @Override public boolean isEmpty() {
        return this.wrapped.isEmpty();
    }
    
    @Override public void setStack(ItemStackAPI<?> stack, int slot) {
        this.wrapped.setItem(slot,stack.unwrap());
    }
}