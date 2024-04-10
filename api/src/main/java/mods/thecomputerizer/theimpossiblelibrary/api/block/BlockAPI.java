package mods.thecomputerizer.theimpossiblelibrary.api.block;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;

public interface BlockAPI<B> {

    RegistryEntryAPI<?> getEntryAPI();
    B getBlock();
}