package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common;

import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.FabricHandlesCommon;
import net.minecraft.core.RegistryAccess;

import java.util.Collections;
import java.util.Set;

import static net.minecraft.world.level.biome.Biome.Precipitation.RAIN;
import static net.minecraft.world.level.biome.Biome.Precipitation.SNOW;

public class FabricHandlesCommon1_16_5 extends FabricHandlesCommon {
    
    @Override public Set<String> biomeTagNames(WorldAPI<?> world, Object biomeObj) {
        Biome biome = (Biome)biomeObj;
        return Collections.singleton(biome.getBiomeCategory().getName());
    }
    
    @Override public Object builtInRegistryAccess() {
        return RegistryAccess.builtin();
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