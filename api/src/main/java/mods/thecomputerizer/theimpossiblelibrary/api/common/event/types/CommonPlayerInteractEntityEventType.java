package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;

public abstract class CommonPlayerInteractEntityEventType<E> extends CommonPlayerInteractEventType<E> {

    protected EventFieldWrapper<E,EntityAPI<?>> target;

    protected CommonPlayerInteractEntityEventType(CommonType<?> type) {
        super(type);
    }

    public EntityAPI<?> getTarget() {
        return this.target.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.target = wrapTargetField();
    }

    protected abstract EventFieldWrapper<E,EntityAPI<?>> wrapTargetField();
}