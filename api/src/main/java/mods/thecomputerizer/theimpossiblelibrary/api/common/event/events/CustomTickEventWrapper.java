package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.CUSTOM_TICK;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickType.CUSTOM;

public abstract class CustomTickEventWrapper<E> extends CommonTickableEventType<E> { //TODO

    protected CustomTickEventWrapper() {
        super(CUSTOM_TICK);
    }

    @Override
    protected TickPhase wrapTickPhase() {
        return DEFAULT;
    }

    @Override
    protected TickType wrapTickType() {
        return CUSTOM;
    }
}