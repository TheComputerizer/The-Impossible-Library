package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.TickableEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.TICK_PLAYER;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.TickableEventWrapper.TickType.PLAYER;

public abstract class PlayerTickEventWrapper<E> extends CommonEventWrapper<E> implements TickableEventWrapper {

    protected PlayerTickEventWrapper() {
        super(TICK_PLAYER);
    }

    @Override
    public TickType getTickType() {
        return PLAYER;
    }

    @Override
    protected void populate() {}
}