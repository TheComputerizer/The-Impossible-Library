package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.container;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.container.PlayerInventory1_16_5;
import net.minecraft.entity.player.PlayerInventory;

import java.util.Collection;
import java.util.stream.Collectors;

public class PlayerInventoryForge1_16_5 extends PlayerInventory1_16_5<PlayerInventory> {

    public PlayerInventoryForge1_16_5(Object inventory) {
        super((PlayerInventory)inventory);
    }
    
    @Override public Collection<ItemStackAPI<?>> getArmorStacks() {
        return this.wrapped.armor.stream().map(WrapperHelper::wrapItemStack).collect(Collectors.toList());
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