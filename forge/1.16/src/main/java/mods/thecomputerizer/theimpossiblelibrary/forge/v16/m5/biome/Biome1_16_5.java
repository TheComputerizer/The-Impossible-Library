package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.RegistryEntry1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.Registry1_16_5;
import net.minecraft.world.biome.Biome;

public class Biome1_16_5 extends RegistryEntry1_16_5<Biome> implements BiomeAPI<Biome> {

    private final Biome biome;

    public Biome1_16_5(Biome biome) {
        super(biome);
        this.biome = biome;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Registry1_16_5<Biome> getRegistry() {
        return (Registry1_16_5<Biome>)(RegistryAPI<?>)RegistryHelper.getBiomeRegistry();
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
