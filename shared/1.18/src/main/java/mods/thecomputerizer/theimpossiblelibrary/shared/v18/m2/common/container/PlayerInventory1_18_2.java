package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.container;

import mods.thecomputerizer.theimpossiblelibrary.api.common.container.PlayerInventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static net.minecraft.world.item.ItemStack.EMPTY;

public class PlayerInventory1_18_2 extends PlayerInventoryAPI<Inventory> {
    
    public PlayerInventory1_18_2(Object inventory) {
        super(inventory);
    }
    
    @Override public Collection<ItemStackAPI<?>> getArmorStacks() {
        return getStacks(w -> w.armor);
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
        return getStacks(w -> w.items);
    }
    
    @Override public Collection<ItemStackAPI<?>> getOffHandStacks() {
        return getStacks(w -> w.offhand);
    }
    
    @Override public ItemStackAPI<?> getStack(int slot) {
        return WrapperHelper.wrapItemStack(getIfNotNullOrDefault(w -> w.getItem(slot),EMPTY));
    }
    
    @Override public int getSlots() {
        return getIfNotNullOrDefault(Container::getContainerSize, 0);
    }
    
    @Override public boolean isEmpty() {
        return getIfNotNullOrDefault(Container::isEmpty,true);
    }
    
    @Override public void setStack(ItemStackAPI<?> stack, int slot) {
        if(Objects.nonNull(this.wrapped)) this.wrapped.setItem(slot, stack.unwrap());
    }
}