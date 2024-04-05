package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_XP_CHANGE;

public abstract class PlayerChangeXPEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerChangeXPEventWrapper() {
        super(PLAYER_XP_CHANGE);
    }

    @Override
    protected void populate() {

    }
}