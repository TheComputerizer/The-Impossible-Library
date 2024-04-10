package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractEntityEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_ENTITY;

public abstract class PlayerInteractEntityEventWrapper<E> extends CommonPlayerInteractEntityEventType<E> {

    protected PlayerInteractEntityEventWrapper() {
        super(PLAYER_INTERACT_ENTITY);
    }
}