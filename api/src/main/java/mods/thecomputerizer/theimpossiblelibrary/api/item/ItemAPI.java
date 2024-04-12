package mods.thecomputerizer.theimpossiblelibrary.api.item;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

public interface ItemAPI<I> {

    RegistryEntryAPI<?> getEntryAPI();
    I getItem();

    default ResourceLocationAPI<?> getRegistryName() {
        return getEntryAPI().getRegistryKey();
    }
}