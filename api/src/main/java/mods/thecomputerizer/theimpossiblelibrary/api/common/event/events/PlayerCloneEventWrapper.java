package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_CLONE;

public abstract class PlayerCloneEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerCloneEventWrapper() {
        super(PLAYER_CLONE);
    }

    @Override
    protected void populate() {

    }
}