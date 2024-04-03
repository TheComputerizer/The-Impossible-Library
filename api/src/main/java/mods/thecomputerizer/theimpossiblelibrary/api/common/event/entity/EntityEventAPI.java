package mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.RegistryObjectEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;

public interface EntityEventAPI extends RegistryObjectEventAPI {

    <E> E getEntity();
    EntityAPI<?> getEntityAPI();
}