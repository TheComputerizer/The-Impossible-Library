package mods.thecomputerizer.theimpossiblelibrary.api.common.container;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class PlayerInventoryAPI<I> extends InventoryAPI<I> {

    protected PlayerInventoryAPI(Object inventory) {
        super(inventory);
    }

    @IndirectCallers public abstract Collection<ItemStackAPI<?>> getArmorStacks();
    @IndirectCallers public abstract Collection<ItemStackAPI<?>> getHotbarStacks();
    @IndirectCallers public abstract Collection<ItemStackAPI<?>> getMainStacks();
    @IndirectCallers public abstract Collection<ItemStackAPI<?>> getOffHandStacks();
    
    protected final Collection<ItemStackAPI<?>> getStacks(Function<I,List<?>> getStacksFunc) {
        List<?> stacks = getIfNotNullOrDefault(getStacksFunc,Collections.emptyList());
        return stacks.stream().map(WrapperHelper::wrapItemStack).collect(Collectors.toList());
    }
}