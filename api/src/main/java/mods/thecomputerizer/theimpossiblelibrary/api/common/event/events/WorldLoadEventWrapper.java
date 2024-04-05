package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_LOAD;

public abstract class WorldLoadEventWrapper<E> extends CommonEventWrapper<E> {

    protected WorldLoadEventWrapper() {
        super(WORLD_LOAD);
    }

    @Override
    protected void populate() {

    }
}