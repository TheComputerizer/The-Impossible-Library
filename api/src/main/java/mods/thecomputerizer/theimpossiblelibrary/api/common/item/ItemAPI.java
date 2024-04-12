package mods.thecomputerizer.theimpossiblelibrary.api.common.item;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;

@Getter
public abstract class ItemAPI<I> implements RegistryEntryAPI<I> {

    protected final I item;

    protected ItemAPI(I item) {
        this.item = item;
    }

    @Override
    public I getValue() {
        return this.item;
    }

    @SuppressWarnings("unchecked")
    public Class<? extends I> getValueClass() {
        return (Class<? extends I>)this.item.getClass();
    }
}