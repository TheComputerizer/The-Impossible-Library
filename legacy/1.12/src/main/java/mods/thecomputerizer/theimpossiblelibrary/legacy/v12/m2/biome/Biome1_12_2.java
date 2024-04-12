package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.RegistryEntry1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.Registry1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.BlockPos1_12_2;
import net.minecraft.world.biome.Biome;

import java.util.Collections;
import java.util.List;

public class Biome1_12_2 extends RegistryEntry1_12_2<Biome> implements BiomeAPI<Biome> {

    private final Biome biome;

    public Biome1_12_2(Biome biome) {
        super(biome);
        this.biome = biome;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Registry1_12_2<Biome> getRegistry() {
        return (Registry1_12_2<Biome>)(RegistryAPI<?>)RegistryHelper.getBiomeRegistry();
    }

    @Override
    public RegistryEntryAPI<Biome> getEntryAPI() {
        return this;
    }

    @Override
    public float getRainfall() {
        return this.biome.getRainfall();
    }

    @Override
    public List<String> getTagNames() {
        return Collections.emptyList();
    }

    @Override
    public float getTemperatureAt(BlockPosAPI<?> pos) {
        return this.biome.getTemperature(((BlockPos1_12_2)pos).getPos());
    }

    @Override
    public boolean canRain() {
        return this.biome.canRain();
    }

    @Override
    public boolean canSnow() {
        return this.biome.isSnowyBiome();
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
