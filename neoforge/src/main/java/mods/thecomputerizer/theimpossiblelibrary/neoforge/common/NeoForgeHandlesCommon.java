package mods.thecomputerizer.theimpossiblelibrary.neoforge.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import java.util.Collections;
import java.util.Set;

/**
 * NeoForge placeholder implementation. None of these methods are needed in 1.20.4+
 */
public class NeoForgeHandlesCommon implements SharedHandlesCommon {
    
    @Override public Set<String> biomeTagNames(WorldAPI<?> world, Object biome) {
        return Collections.emptySet();
    }
    
    @Override public Object builtInRegistryAccess() {
        return null;
    }
    
    @Override public boolean canBiomeRain(Object biome, WorldAPI<?> world, BlockPosAPI<?> pos) {
        return false;
    }
    
    @Override public boolean canBiomeSnow(Object biome, WorldAPI<?> world, BlockPosAPI<?> pos) {
        return false;
    }
}