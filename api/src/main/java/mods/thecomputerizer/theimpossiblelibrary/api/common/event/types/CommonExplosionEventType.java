package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.ExplosionAPI;

public abstract class CommonExplosionEventType<E> extends CommonWorldEventType<E> {

    protected EventFieldWrapper<E,ExplosionAPI<?>> explosion;

    protected CommonExplosionEventType(CommonType<?> type) {
        super(type);
    }

    public ExplosionAPI<?> getExplosion() {
        return this.explosion.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.explosion = wrapExplosionField();
    }

    protected abstract EventFieldWrapper<E,ExplosionAPI<?>> wrapExplosionField();
}