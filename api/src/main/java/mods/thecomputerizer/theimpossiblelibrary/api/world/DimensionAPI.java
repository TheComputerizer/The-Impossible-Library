package mods.thecomputerizer.theimpossiblelibrary.api.world;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;

public abstract class DimensionAPI<D> extends AbstractWrapped<D> {

    /**
     * The world is necessary for any versions past 1.12.2 to access the dynamic registries
     */
    @SuppressWarnings("unused")
    protected DimensionAPI(WorldAPI<?> world, D dimension) {
        super(dimension);
    }
    
    protected @Nullable ResourceLocationAPI<?> cacheRegistryName(WorldAPI<?> api) {
        return Objects.nonNull(api) ? WrapperHelper.wrapResourceLocation(api.unwrap(),levelLocator()) : null;
    }

    public abstract String getName();
    
    /**
     * Returns null if the registry entry for the wrapped dimension type unable to be found.
     * May also return null if a network issue occurs when logging into a server.
     */
    public abstract @Nullable ResourceLocationAPI<?> getRegistryName();
    
    /**
     * Get the ResourceLocation from the Level object
     */
    protected abstract Function<Object,Object> levelLocator();
}