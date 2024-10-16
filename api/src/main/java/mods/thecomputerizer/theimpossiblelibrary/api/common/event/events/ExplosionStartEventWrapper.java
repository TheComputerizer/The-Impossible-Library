package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonExplosionEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.EXPLOSION_START;

public abstract class ExplosionStartEventWrapper<E> extends CommonExplosionEventType<E> {

    protected ExplosionStartEventWrapper() {
        super(EXPLOSION_START);
    }
}