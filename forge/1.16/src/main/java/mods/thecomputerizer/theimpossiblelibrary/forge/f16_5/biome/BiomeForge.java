package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.RegistryEntryForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.RegistryForge;
import net.minecraft.world.biome.Biome;

public class BiomeForge extends RegistryEntryForge<Biome> implements BiomeAPI<Biome> {

    private final Biome biome;

    public BiomeForge(Biome biome) {
        super(biome);
        this.biome = biome;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RegistryForge<Biome> getRegistry() {
        return (RegistryForge<Biome>)(RegistryAPI<?>)RegistryHelper.getBiomeRegistry();
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
        return this.biome.getClass();
    }
}
