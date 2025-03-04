package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import java.util.HashSet;
import java.util.Set;

public class Biome1_12_2 extends BiomeAPI<Biome> {

    public Biome1_12_2(Object biome) {
        super((Biome)biome);
    }
    
    @Override public boolean canRain(WorldAPI<?> world, BlockPosAPI<?> pos) {
        return this.wrapped.canRain() && !canSnow(world,pos);
    }
    
    @Override public boolean canSnow(WorldAPI<?> world, BlockPosAPI<?> pos) {
        return ((World)world.unwrap()).canSnowAt(pos.unwrap(),false);
    }

    @Override public float getRainfall() {
        return this.wrapped.getRainfall();
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName(WorldAPI<?> world) {
        return getRegistryName();
    }
    
    @Override public Set<String> getTagNames(WorldAPI<?> world) {
        Set<String> tags = new HashSet<>();
        for(Type type : BiomeDictionary.getTypes(this.wrapped)) tags.add(type.getName());
        return tags;
    }

    @Override public float getTemperatureAt(BlockPosAPI<?> pos) {
        return this.wrapped.getTemperature(pos.unwrap());
    }
    
    @Override public String getName(WorldAPI<?> world) {
        return CoreAPI.isClient() ? this.wrapped.getBiomeName() : null;
    }
}