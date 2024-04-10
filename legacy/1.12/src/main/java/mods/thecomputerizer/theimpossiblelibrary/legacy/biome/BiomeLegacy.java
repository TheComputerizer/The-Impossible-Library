package mods.thecomputerizer.theimpossiblelibrary.legacy.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.RegistryEntryLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.RegistryLegacy;
import net.minecraft.world.biome.Biome;

public class BiomeLegacy extends RegistryEntryLegacy<Biome> implements BiomeAPI<Biome> {

    private final Biome biome;

    public BiomeLegacy(Biome biome) {
        super(biome);
        this.biome = biome;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RegistryLegacy<Biome> getRegistry() {
        return (RegistryLegacy<Biome>)(RegistryAPI<?>)RegistryHelper.getBiomeRegistry();
    }

    @Override
    public RegistryEntryAPI<Biome> getEntryAPI() {
        return this;
    }

    @Override
    public Biome getBiome() {
        return this.biome;
    }

    @Override
    public Class<? extends Biome> getValueClass() {
        return this.biome.getBiomeClass();
    }
}
