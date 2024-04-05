package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_BREAK_SPEED;

public abstract class PlayerBreakSpeedEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerBreakSpeedEventWrapper() {
        super(PLAYER_BREAK_SPEED);
    }

    @Override
    protected void populate() {

    }
}