package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_SAVE;

public abstract class WorldSaveEventWrapper<E> extends CommonEventWrapper<E> {

    protected WorldSaveEventWrapper() {
        super(WORLD_SAVE);
    }

    @Override
    protected void populate() {

    }
}