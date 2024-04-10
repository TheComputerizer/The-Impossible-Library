package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractEntityEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_PUNCH_ENTITY;

public abstract class PlayerPunchEntityEventWrapper<E> extends CommonPlayerInteractEntityEventType<E> {

    protected PlayerPunchEntityEventWrapper() {
        super(PLAYER_PUNCH_ENTITY);
    }
}