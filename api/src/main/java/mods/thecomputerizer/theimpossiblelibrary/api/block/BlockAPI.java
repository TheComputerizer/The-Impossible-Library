package mods.thecomputerizer.theimpossiblelibrary.api.block;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

public interface BlockAPI<B> {

    B getBlock();
    RegistryEntryAPI<?> getEntryAPI();

    default ResourceLocationAPI<?> getRegistryName() {
        return getEntryAPI().getRegistryKey();
    }
}