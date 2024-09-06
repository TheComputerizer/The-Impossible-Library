package mods.thecomputerizer.theimpossiblelibrary.api.common.container;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.util.Collection;

public abstract class PlayerInventoryAPI<I> extends InventoryAPI<I> {

    protected PlayerInventoryAPI(I inventory) {
        super(inventory);
    }

    @IndirectCallers public abstract Collection<ItemStackAPI<?>> getArmorStacks();
    @IndirectCallers public abstract Collection<ItemStackAPI<?>> getHotbarStacks();
    @IndirectCallers public abstract Collection<ItemStackAPI<?>> getMainStacks();
    @IndirectCallers public abstract Collection<ItemStackAPI<?>> getOffHandStacks();
}