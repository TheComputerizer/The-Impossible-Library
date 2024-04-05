package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_ITEM;

public abstract class PlayerInteractItemEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerInteractItemEventWrapper() {
        super(PLAYER_INTERACT_ITEM);
    }

    @Override
    protected void populate() {

    }
}
