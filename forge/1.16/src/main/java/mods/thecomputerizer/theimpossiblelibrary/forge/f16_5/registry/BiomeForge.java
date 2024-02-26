package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import net.minecraft.world.biome.Biome;

public class BiomeForge extends RegistryEntryForge<Biome> implements BiomeAPI<Biome> {

    private final Biome biome;

    protected BiomeForge(Biome entry) {
        super(entry);
        this.biome = entry;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RegistryForge<Biome> getRegistry() {
        RegistryAPI<?> api = RegistryHelper.getBiomeRegistry();
        return (RegistryForge<Biome>)api;
    }

    @Override
    public Biome getBiome() {
        return this.biome;
    }
}
