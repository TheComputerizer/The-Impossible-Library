package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.container;

import mods.thecomputerizer.theimpossiblelibrary.api.common.container.PlayerInventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class PlayerInventory1_16_5<P> extends PlayerInventoryAPI<P> {

    protected PlayerInventory1_16_5(P inventory) {
        super(inventory);
    }

    @Override public Collection<ItemStackAPI<?>> getHotbarStacks() {
        List<ItemStackAPI<?>> stacks = new ArrayList<>();
        for(int i=0;i<9;i++) {
            ItemStackAPI<?> stack = getStack(i);
            if(stack.isNotEmpty()) stacks.add(stack);
        }
        return stacks;
    }
}