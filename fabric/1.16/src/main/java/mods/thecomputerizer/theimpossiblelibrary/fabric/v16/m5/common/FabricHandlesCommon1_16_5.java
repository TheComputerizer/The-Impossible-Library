package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common;

import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.FabricHandlesCommon;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.biome.Biome;

import java.util.Collections;
import java.util.Set;

import static net.minecraft.world.level.biome.Biome.Precipitation.RAIN;
import static net.minecraft.world.level.biome.Biome.Precipitation.SNOW;

public class FabricHandlesCommon1_16_5 extends FabricHandlesCommon {
    
    @Override public Set<String> biomeTagNames(WorldAPI<?> world, Object biome) {
        return Collections.singleton(((Biome)biome).getBiomeCategory().getName());
    }
    
    @Override public Object builtInRegistryAccess() {
        return RegistryAccess.builtin();
    }
    
    @Override public boolean canBiomeRain(Object biome) {
        return ((Biome)biome).getPrecipitation()==RAIN;
    }
    
    @Override public boolean canBiomeSnow(Object biome) {
        return ((Biome)biome).getPrecipitation()==SNOW;
    }
}