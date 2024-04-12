package mods.thecomputerizer.theimpossiblelibrary.api.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

public interface BlockEntityAPI<BE> {

    BE getBlockEntity();
    RegistryEntryAPI<?> getEntryAPI();

    default ResourceLocationAPI<?> getRegistryName() {
        return getEntryAPI().getRegistryKey();
    }
}