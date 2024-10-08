package mods.thecomputerizer.theimpossiblelibrary.fabric.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.world.level.biome.Biome;

import java.util.Collections;
import java.util.Set;

public class FabricHandlesCommon implements SharedHandlesCommon {
    
    @Override public Set<String> biomeTagNames(WorldAPI<?> world, Object biome) {
        return Collections.singleton(((Biome)biome).getBiomeCategory().getName());
    }
}