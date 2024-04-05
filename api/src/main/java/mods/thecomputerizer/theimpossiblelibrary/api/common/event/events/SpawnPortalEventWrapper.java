package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_SPAWN_PORTAL;

public abstract class SpawnPortalEventWrapper<E> extends CommonEventWrapper<E> {

    protected SpawnPortalEventWrapper() {
        super(BLOCK_SPAWN_PORTAL);
    }

    @Override
    protected void populate() {

    }
}