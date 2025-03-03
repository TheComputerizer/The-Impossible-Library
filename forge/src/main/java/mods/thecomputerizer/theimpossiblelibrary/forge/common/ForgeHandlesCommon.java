package mods.thecomputerizer.theimpossiblelibrary.forge.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import java.util.Collections;
import java.util.Set;

public class ForgeHandlesCommon implements SharedHandlesCommon {
    
    @Override public Set<String> biomeTagNames(WorldAPI<?> world, Object biome) {
        return Collections.emptySet(); //Not needed in 1.18.2+
    }
    
    @Override public Object builtInRegistryAccess() {
        return null; //Not needed in 1.18.2+
    }
    
    @Override public boolean canBiomeRain(Object biome) {
        return false; //Not needed in 1.18.2+
    }
    
    @Override public boolean canBiomeSnow(Object biome) {
        return false; //Not needed in 1.18.2+
    }
}