package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_LOGGED_IN;

public abstract class PlayerLoggedInEventWrapper<E> extends CommonPlayerEventType<E> {

    protected PlayerLoggedInEventWrapper() {
        super(PLAYER_LOGGED_IN);
    }
}