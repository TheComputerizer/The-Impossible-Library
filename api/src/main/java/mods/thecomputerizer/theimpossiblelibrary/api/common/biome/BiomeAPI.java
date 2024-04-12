package mods.thecomputerizer.theimpossiblelibrary.api.common.biome;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import java.util.Set;

@Getter
public abstract class BiomeAPI<B> implements RegistryEntryAPI<B> {

    protected final B biome;

    protected BiomeAPI(B biome) {
        this.biome = biome;
    }

    public abstract boolean canRain();
    public abstract boolean canSnow();
    public abstract float getRainfall();
    public abstract Set<String> getTagNames(WorldAPI<?> world);
    public abstract float getTemperatureAt(BlockPosAPI<?> pos);

    @Override
    public B getValue() {
        return this.biome;
    }

    @SuppressWarnings("unchecked")
    public Class<? extends B> getValueClass() {
        return (Class<? extends B>)this.biome.getClass();
    }
}
