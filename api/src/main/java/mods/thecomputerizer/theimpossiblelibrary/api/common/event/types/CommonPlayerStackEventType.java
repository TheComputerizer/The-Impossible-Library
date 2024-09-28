package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;

public abstract class CommonPlayerStackEventType<E> extends CommonPlayerEventType<E> {

    protected EventFieldWrapper<E,ItemStackAPI<?>> stack;

    protected CommonPlayerStackEventType(CommonType<?> type) {
        super(type);
    }

    @Override public void populate() {
        super.populate();
        this.stack = wrapStackField();
    }

    protected abstract EventFieldWrapper<E,ItemStackAPI<?>> wrapStackField();
}
