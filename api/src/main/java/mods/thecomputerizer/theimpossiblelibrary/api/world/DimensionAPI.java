package mods.thecomputerizer.theimpossiblelibrary.api.world;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

@Getter
public abstract class DimensionAPI<D> {

    protected final D dimension;

    /**
     * The world is needed for any versions past 1.12.2 to access the dynamic registries
     */
    protected DimensionAPI(WorldAPI<?> world, D dimension) {
        this.dimension = dimension;
    }

    public abstract ResourceLocationAPI<?> getRegistryName();
}
