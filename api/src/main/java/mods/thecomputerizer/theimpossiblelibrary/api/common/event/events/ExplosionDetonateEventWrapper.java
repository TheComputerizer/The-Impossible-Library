package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.EXPLOSION_DETONATE;

public abstract class ExplosionDetonateEventWrapper<E> extends CommonEventWrapper<E> {

    protected ExplosionDetonateEventWrapper() {
        super(EXPLOSION_DETONATE);
    }

    @Override
    protected void populate() {

    }
}