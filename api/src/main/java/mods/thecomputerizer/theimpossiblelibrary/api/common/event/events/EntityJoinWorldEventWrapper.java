package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ENTITY_JOIN_WORLD;

public abstract class EntityJoinWorldEventWrapper<E> extends CommonEventWrapper<E> {

    protected EntityJoinWorldEventWrapper() {
        super(ENTITY_JOIN_WORLD);
    }

    @Override
    protected void populate() {

    }
}