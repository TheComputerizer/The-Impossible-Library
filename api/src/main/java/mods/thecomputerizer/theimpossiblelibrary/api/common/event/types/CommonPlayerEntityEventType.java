package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;

public abstract class CommonPlayerEntityEventType<E> extends CommonPlayerEventType<E> {

    protected EventFieldWrapper<E,EntityAPI<?>> entity;

    protected CommonPlayerEntityEventType(CommonType<?> type) {
        super(type);
    }

    public void populate() {
        super.populate();
        this.entity = wrapEntityField();
    }

    protected abstract EventFieldWrapper<E,EntityAPI<?>> wrapEntityField();
}
