package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;

public abstract class CommonBlockStateEntityEventType<E> extends CommonBlockStateEventType<E> {

    protected EventFieldWrapper<E,EntityAPI<?>> entity;

    protected CommonBlockStateEntityEventType(CommonType<?> type) {
        super(type);
    }

    public EntityAPI<?> getEntity() {
        return this.entity.get(this.event);
    }

    @Override
    protected void populate() {
        super.populate();
        this.entity = wrapEntityField();
    }

    protected abstract EventFieldWrapper<E,EntityAPI<?>> wrapEntityField();
}