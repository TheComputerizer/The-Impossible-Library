package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import java.util.Set;

/**
 * Common mod loader stuff that is otherwise annoying to abstract
 */
public interface SharedHandlesCommon {
    
    Set<String> biomeTagNames(WorldAPI<?> world, Object biome);
    boolean canBiomeRain(Object biome);
    boolean canBiomeSnow(Object biome);
}