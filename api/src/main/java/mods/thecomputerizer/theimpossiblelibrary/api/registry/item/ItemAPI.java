package mods.thecomputerizer.theimpossiblelibrary.api.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;

public interface ItemAPI<I> {

    RegistryEntryAPI<?> getEntryAPI();
    I getItem();
}