package mods.thecomputerizer.theimpossiblelibrary.api.world;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

public interface DimensionAPI<D> {

    D getDimension();
    ResourceLocationAPI<?> getRegistryName();
}
