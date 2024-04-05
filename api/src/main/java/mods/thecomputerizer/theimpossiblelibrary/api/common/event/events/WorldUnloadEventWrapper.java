package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_UNLOAD;

public abstract class WorldUnloadEventWrapper<E> extends CommonEventWrapper<E> {

    protected WorldUnloadEventWrapper() {
        super(WORLD_UNLOAD);
    }

    @Override
    protected void populate() {

    }
}