package mods.thecomputerizer.theimpossiblelibrary.api.common.container;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;

import java.util.Collection;

public abstract class PlayerInventoryAPI<I> extends InventoryAPI<I> {

    protected PlayerInventoryAPI(I inventory) {
        super(inventory);
    }

    public abstract Collection<ItemStackAPI<?>> getArmorStacks();
    public abstract Collection<ItemStackAPI<?>> getHotbarStacks();
    public abstract Collection<ItemStackAPI<?>> getMainStacks();
    public abstract Collection<ItemStackAPI<?>> getOffHandStacks();
}
