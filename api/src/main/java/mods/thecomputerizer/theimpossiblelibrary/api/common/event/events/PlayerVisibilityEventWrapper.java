package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_VISIBILITY;

public abstract class PlayerVisibilityEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerVisibilityEventWrapper() {
        super(PLAYER_VISIBILITY);
    }

    @Override
    protected void populate() {

    }
}