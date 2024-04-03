package mods.thecomputerizer.theimpossiblelibrary.api.registry.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;

public interface EntityAPI<E> {

    E getEntity();
    RegistryEntryAPI<?> getEntryAPI();
    boolean isLiving();
    boolean isPlayer();
}
