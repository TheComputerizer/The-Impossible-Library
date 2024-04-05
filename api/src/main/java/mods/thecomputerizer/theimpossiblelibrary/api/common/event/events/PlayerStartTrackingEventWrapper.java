package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_START_TRACKING;

public abstract class PlayerStartTrackingEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerStartTrackingEventWrapper() {
        super(PLAYER_START_TRACKING);
    }

    @Override
    protected void populate() {

    }
}