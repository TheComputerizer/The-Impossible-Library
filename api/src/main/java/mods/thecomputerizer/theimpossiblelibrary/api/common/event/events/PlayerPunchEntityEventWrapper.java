package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_PUNCH_ENTITY;

public abstract class PlayerPunchEntityEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerPunchEntityEventWrapper() {
        super(PLAYER_PUNCH_ENTITY);
    }

    @Override
    protected void populate() {

    }
}