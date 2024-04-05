package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_LOGGED_OUT;

public abstract class PlayerLoggedOutEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerLoggedOutEventWrapper() {
        super(PLAYER_LOGGED_OUT);
    }

    @Override
    protected void populate() {

    }
}