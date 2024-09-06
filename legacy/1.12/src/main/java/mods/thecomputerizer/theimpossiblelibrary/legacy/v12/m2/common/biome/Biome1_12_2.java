package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import java.util.HashSet;
import java.util.Set;

public class Biome1_12_2 extends BiomeAPI<Biome> {

    public Biome1_12_2(Biome biome) {
        super(biome);
    }

    @Override public float getRainfall() {
        return this.wrapped.getRainfall();
    }

    @Override public Set<String> getTagNames(WorldAPI<?> world) {
        Set<String> tags = new HashSet<>();
        for(Type type : BiomeDictionary.getTypes(this.wrapped)) tags.add(type.getName());
        return tags;
    }

    @Override public float getTemperatureAt(BlockPosAPI<?> pos) {
        return this.wrapped.getTemperature(pos.unwrap());
    }

    @Override public boolean canRain() {
        return this.wrapped.canRain();
    }

    @Override public boolean canSnow() {
        return this.wrapped.isSnowyBiome();
    }
}