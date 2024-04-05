package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_LOGGED_IN;

public abstract class PlayerLoggedInEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerLoggedInEventWrapper() {
        super(PLAYER_LOGGED_IN);
    }

    @Override
    protected void populate() {

    }
}