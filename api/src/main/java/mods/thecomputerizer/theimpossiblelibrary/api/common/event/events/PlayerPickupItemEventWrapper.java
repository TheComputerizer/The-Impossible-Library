package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ITEM_PICKUP;

public abstract class PlayerPickupItemEventWrapper<E> extends CommonEventWrapper<E> {
    protected PlayerPickupItemEventWrapper() {
        super(PLAYER_ITEM_PICKUP);
    }

    @Override
    protected void populate() {

    }
}