package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common;

import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.ForgeHandlesCommon;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static net.minecraft.util.registry.Registry.BIOME_REGISTRY;
import static net.minecraft.world.biome.Biome.RainType.RAIN;
import static net.minecraft.world.biome.Biome.RainType.SNOW;

public class ForgeHandlesCommon1_16_5 extends ForgeHandlesCommon {
    
    @Override public Set<String> biomeTagNames(WorldAPI<?> worldAPI, Object biomeObj) {
        Biome biome = (Biome)biomeObj;
        IWorld world = worldAPI.unwrap();
        DynamicRegistries registries = world.registryAccess();
        Registry<Biome> registry = registries.registry(BIOME_REGISTRY).orElse(null);
        if(Objects.isNull(registry)) return Collections.emptySet();
        RegistryKey<Biome> key = registry.getResourceKey(biome).orElse(null);
        if(Objects.isNull(key)) return Collections.emptySet();
        return BiomeDictionary.getTypes(key).stream().map(Type::getName).collect(Collectors.toSet());
    }
    
    @Override public Object builtInRegistryAccess() {
        return DynamicRegistries.builtin();
    }
    
    @Override public boolean canBiomeRain(Object biomeObj, WorldAPI<?> world, BlockPosAPI<?> pos) {
        Biome biome = (Biome)biomeObj;
        return biome.getPrecipitation()==RAIN && !biome.shouldSnow(world.unwrap(),pos.unwrap());
    }
    
    @Override public boolean canBiomeSnow(Object biomeObj, WorldAPI<?> world, BlockPosAPI<?> pos) {
        Biome biome = (Biome)biomeObj;
        return biome.getPrecipitation()==SNOW && biome.shouldSnow(world.unwrap(),pos.unwrap());
    }
}