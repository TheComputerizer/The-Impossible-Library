package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_STOP_TRACKING;

public abstract class PlayerStopTrackingEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerStopTrackingEventWrapper() {
        super(PLAYER_STOP_TRACKING);
    }

    @Override
    protected void populate() {

    }
}