package mods.thecomputerizer.theimpossiblelibrary.api.world;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;
import org.jetbrains.annotations.Nullable;

public abstract class DimensionAPI<D> extends AbstractWrapped<D> {

    /**
     * The world is necessary for any versions past 1.12.2 to access the dynamic registries
     */
    @SuppressWarnings("unused")
    protected DimensionAPI(WorldAPI<?> world, D dimension) {
        super(dimension);
    }

    public abstract String getName();
    
    /**
     * Returns null if the registry entry for the wrapped dimension type unable to be found.
     * May also return null if a network issue occurs when logging into a server.
     */
    public abstract @Nullable ResourceLocationAPI<?> getRegistryName();
}