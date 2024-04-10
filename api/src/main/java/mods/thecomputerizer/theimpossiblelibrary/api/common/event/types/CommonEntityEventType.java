package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;

public abstract class CommonEntityEventType<E> extends CommonEventWrapper<E> {

    protected EventFieldWrapper<E,EntityAPI<?>> entity;

    protected CommonEntityEventType(CommonType<?> type) {
        super(type);
    }

    public EntityAPI<?> getEntity() {
        return this.entity.get(this.event);
    }

    @Override
    protected void populate() {
        this.entity = wrapEntityField();
    }

    protected abstract EventFieldWrapper<E,EntityAPI<?>> wrapEntityField();
}
