package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_ENTITY;

public abstract class PlayerInteractEntityEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerInteractEntityEventWrapper() {
        super(PLAYER_INTERACT_ENTITY);
    }

    @Override
    protected void populate() {

    }
}