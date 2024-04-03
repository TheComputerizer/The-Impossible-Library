package mods.thecomputerizer.theimpossiblelibrary.api.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;

public interface BlockAPI<B> {

    RegistryEntryAPI<?> getEntryAPI();
    B getBlock();
}