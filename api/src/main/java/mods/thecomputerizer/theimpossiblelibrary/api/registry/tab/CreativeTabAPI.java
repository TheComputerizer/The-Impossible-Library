package mods.thecomputerizer.theimpossiblelibrary.api.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

import java.util.ArrayList;
import java.util.List;

public abstract class CreativeTabAPI<T> extends AbstractWrapped<T> {
    
    protected final List<ItemStackAPI<?>> stacks = new ArrayList<>();
    
    protected CreativeTabAPI(T wrapped) {
        super(wrapped);
    }
    
    public void addItem(ItemAPI<?> item) {
        addStack(item.defaultStack());
    }
    
    public abstract void addStack(ItemStackAPI<?> stack);
    public abstract ItemStackAPI<?> getIcon(); //Only accessible on the client side
    public abstract <P> P withItemProperties(P properties);
}