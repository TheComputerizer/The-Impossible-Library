package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonEntityEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ENTITY_SMITTEN;

public abstract class EntityStruckByLightningEventWrapper<E> extends CommonEntityEventType<E> {

    protected EventFieldWrapper<E,EntityAPI<?>> lightningEntity;

    protected EntityStruckByLightningEventWrapper() {
        super(ENTITY_SMITTEN);
    }

    public EntityAPI<?> getLightningEntity() {
        return this.lightningEntity.get(this.event);
    }

    @Override
    protected void populate() {
        super.populate();
        this.lightningEntity = wrapLightningEntityField();
    }

    protected abstract EventFieldWrapper<E,EntityAPI<?>> wrapLightningEntityField();
}