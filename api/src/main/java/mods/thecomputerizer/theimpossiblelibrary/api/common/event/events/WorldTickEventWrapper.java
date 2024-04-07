package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.TickableEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.TICK_WORLD;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.TickableEventWrapper.TickType.WORLD;

public abstract class WorldTickEventWrapper<E> extends CommonEventWrapper<E> implements TickableEventWrapper {

    protected WorldTickEventWrapper() {
        super(TICK_WORLD);
    }

    @Override
    public TickType getTickType() {
        return WORLD;
    }

    @Override
    protected void populate() {}
}