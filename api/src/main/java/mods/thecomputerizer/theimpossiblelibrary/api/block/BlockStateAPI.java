package mods.thecomputerizer.theimpossiblelibrary.api.block;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

public interface BlockStateAPI<S> {

    BlockAPI<?> getBlockAPI();

    default ResourceLocationAPI<?> getRegistryName() {
        return getBlockAPI().getEntryAPI().getRegistryKey();
    }

    S getState();
}