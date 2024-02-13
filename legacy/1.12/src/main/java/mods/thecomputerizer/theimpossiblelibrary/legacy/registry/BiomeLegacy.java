package mods.thecomputerizer.theimpossiblelibrary.legacy.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import net.minecraft.world.biome.Biome;

public class BiomeLegacy extends RegistryEntryLegacy<Biome> implements BiomeAPI<Biome> {

    private final Biome biome;

    protected BiomeLegacy(Biome entry) {
        super(entry);
        this.biome = entry;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RegistryLegacy<Biome> getRegistry() {
        RegistryAPI<?> api = RegistryHelper.getBiomeRegistry();
        return (RegistryLegacy<Biome>)api;
    }

    @Override
    public Biome getBiome() {
        return this.biome;
    }
}
