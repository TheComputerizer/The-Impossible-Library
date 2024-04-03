package mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.LivingEntityAPI;

public interface LivingEventAPI extends EntityEventAPI {

    LivingEntityAPI<?> getLivingAPI();
}