package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.TICK_PLAYER;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickType.PLAYER;

public abstract class PlayerTickEventWrapper<E> extends CommonTickableEventType<E> {

    protected PlayerTickEventWrapper() {
        super(TICK_PLAYER);
    }

    @Override
    protected TickType wrapTickType() {
        return PLAYER;
    }
}