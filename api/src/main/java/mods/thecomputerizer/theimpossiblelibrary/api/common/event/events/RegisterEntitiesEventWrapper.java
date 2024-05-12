package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonRegistryEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_ENTITIES;

public abstract class RegisterEntitiesEventWrapper<E> extends CommonRegistryEventType<E,EntityAPI<?,?>> {

    protected RegisterEntitiesEventWrapper() {
        super(REGISTER_ENTITIES);
    }
}
