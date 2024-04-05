package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.TICK_PLAYER;

public abstract class PlayerTickEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerTickEventWrapper() {
        super(TICK_PLAYER);
    }

    @Override
    protected void populate() {

    }
}