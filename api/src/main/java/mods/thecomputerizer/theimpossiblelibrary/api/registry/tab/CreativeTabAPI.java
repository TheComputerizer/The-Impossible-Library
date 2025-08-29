package mods.thecomputerizer.theimpossiblelibrary.api.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class CreativeTabAPI<T> extends AbstractWrapped<T> {
    
    protected final List<Supplier<ItemStackAPI<?>>> stacks = new ArrayList<>();
    
    protected CreativeTabAPI(Object wrapped) {
        super(wrapped);
    }
    
    @IndirectCallers
    public void addItem(ItemAPI<?> item) {
        addStack(item::defaultStack);
    }
    
    public abstract void addStack(Supplier<ItemStackAPI<?>> stackSupplier);
    public abstract ItemStackAPI<?> getIcon(); //Only accessible on the client side
    public abstract <P> P withItemProperties(P properties);
}