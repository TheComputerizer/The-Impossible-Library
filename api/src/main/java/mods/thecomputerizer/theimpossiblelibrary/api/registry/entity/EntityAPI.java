package mods.thecomputerizer.theimpossiblelibrary.api.registry.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Box;

public interface EntityAPI<E> {

    Box getBoundingBox();
    E getEntity();
    RegistryEntryAPI<?> getEntryAPI();
    boolean isLiving();
    boolean isPlayer();
}
