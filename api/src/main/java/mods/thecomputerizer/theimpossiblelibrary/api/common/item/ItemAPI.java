package mods.thecomputerizer.theimpossiblelibrary.api.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.AbstractWrapped;

public abstract class ItemAPI<I> extends AbstractWrapped<I> implements RegistryEntryAPI<I> {

    protected ItemAPI(I item) {
        super(item);
    }
}