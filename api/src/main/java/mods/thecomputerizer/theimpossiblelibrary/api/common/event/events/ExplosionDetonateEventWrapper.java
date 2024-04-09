package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonExplosionEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;

import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.EXPLOSION_DETONATE;

public abstract class ExplosionDetonateEventWrapper<E> extends CommonExplosionEventType<E> {

    protected EventFieldWrapper<E,List<EntityAPI<?>>> affectedEntities;

    protected ExplosionDetonateEventWrapper() {
        super(EXPLOSION_DETONATE);
    }

    public List<EntityAPI<?>> getAffectedEntities() {
        return this.affectedEntities.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.affectedEntities = wrapAffectedEntitiesField();
    }

    protected abstract EventFieldWrapper<E,List<EntityAPI<?>>> wrapAffectedEntitiesField();
}