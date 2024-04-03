package mods.thecomputerizer.theimpossiblelibrary.api.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;

public interface RegistryObjectEventAPI extends EventAPI {

    RegistryEntryAPI<?> getEntry();
    <V> V getEntryValue();
}