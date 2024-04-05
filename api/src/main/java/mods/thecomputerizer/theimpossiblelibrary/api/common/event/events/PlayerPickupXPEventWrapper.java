package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_XP_PICKUP;

public abstract class PlayerPickupXPEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerPickupXPEventWrapper() {
        super(PLAYER_XP_PICKUP);
    }

    @Override
    protected void populate() {

    }
}