package mods.thecomputerizer.theimpossiblelibrary.api.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.AbstractWrapped;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import java.util.Set;

public abstract class BiomeAPI<B> extends AbstractWrapped<B> implements RegistryEntryAPI<B> {

    protected BiomeAPI(B biome) {
        super(biome);
    }

    @IndirectCallers public abstract boolean canRain();
    @IndirectCallers public abstract boolean canSnow();
    @IndirectCallers public abstract float getRainfall();
    @IndirectCallers public abstract Set<String> getTagNames(WorldAPI<?> world);
    @IndirectCallers public abstract float getTemperatureAt(BlockPosAPI<?> pos);
}