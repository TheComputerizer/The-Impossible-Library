package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

public interface RegistryEntryAPI<V> {

    ResourceLocationAPI<?> getID();
    V getValue();
    ResourceLocationAPI<?> getRegistryKey();
}
