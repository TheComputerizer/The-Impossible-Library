package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Biome1_12_2 extends BiomeAPI<Biome> {

    public Biome1_12_2(Object biome) {
        super(biome);
    }
    
    @Override public boolean canRain(WorldAPI<?> world, BlockPosAPI<?> pos) {
        return getIfNotNullOrDefault(w -> w.canRain() && !canSnow(world,pos),false);
    }
    
    @Override public boolean canSnow(WorldAPI<?> world, BlockPosAPI<?> pos) {
        return ((World)world.unwrap()).canSnowAt(pos.unwrap(),false);
    }

    @Override public float getRainfall() {
        return getIfNotNullOrDefault(Biome::getRainfall,0f);
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName(WorldAPI<?> world) {
        return getRegistryName();
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        if(Objects.nonNull(this.wrapped)) this.wrapped.setRegistryName((ResourceLocation)registryName.unwrap());
    }
    
    @Override public Set<String> getTagNames(WorldAPI<?> world) {
        Set<String> tags = new HashSet<>();
        Set<Type> types = getIfNotNullOrDefault(BiomeDictionary::getTypes,Collections.emptySet());
        for(Type type : types) tags.add(type.getName());
        return tags;
    }

    @Override public float getTemperatureAt(BlockPosAPI<?> pos) {
        return getIfNotNullOrDefault(w -> w.getTemperature(pos.unwrap()),0f);
    }
    
    @Override public String getName(WorldAPI<?> world) {
        return CoreAPI.isClient() ? getIfNotNull(Biome::getBiomeName) : null;
    }
}