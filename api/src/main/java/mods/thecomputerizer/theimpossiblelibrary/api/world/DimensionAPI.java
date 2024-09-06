package mods.thecomputerizer.theimpossiblelibrary.api.world;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.AbstractWrapped;

public abstract class DimensionAPI<D> extends AbstractWrapped<D> {

    /**
     * The world is necessary for any versions past 1.12.2 to access the dynamic registries
     */
    @SuppressWarnings("unused")
    protected DimensionAPI(WorldAPI<?> world, D dimension) {
        super(dimension);
    }

    public abstract String getName();

    public abstract ResourceLocationAPI<?> getRegistryName();
}