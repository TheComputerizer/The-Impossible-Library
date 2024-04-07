package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.TickableEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.CUSTOM_TICK;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.TickableEventWrapper.TickPhase.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.TickableEventWrapper.TickType.CUSTOM;

public abstract class CustomTickEventWrapper<E> extends CommonEventWrapper<E> implements TickableEventWrapper { //TODO

    protected CustomTickEventWrapper() {
        super(CUSTOM_TICK);
    }

    @Override
    public TickPhase getPhase() {
        return DEFAULT;
    }

    @Override
    public TickType getTickType() {
        return CUSTOM;
    }

    @Override
    protected void populate() {}
}