package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.TICK_WORLD;

public abstract class WorldTickEventWrapper<E> extends CommonEventWrapper<E> {

    protected WorldTickEventWrapper() {
        super(TICK_WORLD);
    }

    @Override
    protected void populate() {

    }
}