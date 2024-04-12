package mods.thecomputerizer.theimpossiblelibrary.api.item;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

public interface ItemStackAPI<S> {

    ItemAPI<?> getItemAPI();

    default ResourceLocationAPI<?> getRegistryName() {
        return getItemAPI().getEntryAPI().getRegistryKey();
    }

    S getStack();
}