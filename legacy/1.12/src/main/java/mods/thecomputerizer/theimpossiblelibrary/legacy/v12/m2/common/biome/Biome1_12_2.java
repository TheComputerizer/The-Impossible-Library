package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.BlockPos1_12_2;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import java.util.HashSet;
import java.util.Set;

public class Biome1_12_2 extends BiomeAPI<Biome> {

    public Biome1_12_2(Biome biome) {
        super(biome);
    }

    @Override
    public ResourceLocationAPI<?> getRegistryName() {
        return new ResourceLocation1_12_2(this.biome.getRegistryName());
    }

    @Override
    public float getRainfall() {
        return this.biome.getRainfall();
    }

    @Override
    public Set<String> getTagNames(WorldAPI<?> world) {
        Set<String> tags = new HashSet<>();
        for(Type type : BiomeDictionary.getTypes(this.biome)) tags.add(type.getName());
        return tags;
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
