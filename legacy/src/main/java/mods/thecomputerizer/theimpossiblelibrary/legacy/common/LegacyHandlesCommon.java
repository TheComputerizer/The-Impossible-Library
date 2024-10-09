package mods.thecomputerizer.theimpossiblelibrary.legacy.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import java.util.Collections;
import java.util.Set;

public class LegacyHandlesCommon implements SharedHandlesCommon {
    
    @Override public Set<String> biomeTagNames(WorldAPI<?> world, Object biome) {
        return Collections.emptySet();
    }
    
    @Override public boolean canBiomeRain(Object biome) {
        return false;
    }
    
    @Override public boolean canBiomeSnow(Object biome) {
        return false;
    }
}