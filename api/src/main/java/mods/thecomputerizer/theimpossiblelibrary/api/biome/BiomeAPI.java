package mods.thecomputerizer.theimpossiblelibrary.api.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;

import java.util.List;

public interface BiomeAPI<B> {

    boolean canRain();
    boolean canSnow();
    B getBiome();
    RegistryEntryAPI<?> getEntryAPI();
    float getRainfall();

    default ResourceLocationAPI<?> getRegistryName() {
        return getEntryAPI().getRegistryKey();
    }

    List<String> getTagNames();
    float getTemperatureAt(BlockPosAPI<?> pos);
}
