package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_CREATE_SPAWN_POS;

public abstract class WorldCreateSpawnPosEventWrapper<E> extends CommonEventWrapper<E> {

    protected WorldCreateSpawnPosEventWrapper() {
        super(WORLD_CREATE_SPAWN_POS);
    }

    @Override
    protected void populate() {

    }
}