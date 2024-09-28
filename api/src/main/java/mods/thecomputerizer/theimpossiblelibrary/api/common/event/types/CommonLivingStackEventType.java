package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;

public abstract class CommonLivingStackEventType<E> extends CommonLivingEventType<E> {

    protected EventFieldWrapper<E,ItemStackAPI<?>> stack;

    protected CommonLivingStackEventType(CommonType<?> type) {
        super(type);
    }

    public ItemStackAPI<?> getStack() {
        return this.stack.get(this.event);
    }

    @Override public void populate() {
        super.populate();
        this.stack = wrapStackField();
    }

    protected abstract EventFieldWrapper<E,ItemStackAPI<?>> wrapStackField();
}