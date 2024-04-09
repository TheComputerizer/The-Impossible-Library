package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.TICK_WORLD;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickType.WORLD;

public abstract class WorldTickEventWrapper<E> extends CommonTickableEventType<E> {

    protected WorldTickEventWrapper() {
        super(TICK_WORLD);
    }

    @Override
    protected TickType wrapTickType() {
        return WORLD;
    }
}