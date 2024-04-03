package mods.thecomputerizer.theimpossiblelibrary.api.registry.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;

public interface BiomeAPI<B> {

    RegistryEntryAPI<?> getEntryAPI();
    B getBiome();
}
