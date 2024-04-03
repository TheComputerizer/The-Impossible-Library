package mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;

public interface BlockEntityAPI<BE> {

    RegistryEntryAPI<?> getEntryAPI();
    BE getBlockEntity();
}