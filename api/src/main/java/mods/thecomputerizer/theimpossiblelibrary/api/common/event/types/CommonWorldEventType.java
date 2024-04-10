package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

public abstract class CommonWorldEventType<E> extends CommonEventWrapper<E> {

    protected EventFieldWrapper<E,WorldAPI<?>> world;

    protected CommonWorldEventType(CommonType<?> type) {
        super(type);
    }

    public WorldAPI<?> getWorld() {
        return this.world.get(this.event);
    }

    @Override
    protected void populate() {
        this.world = wrapWorldField();
    }

    protected abstract EventFieldWrapper<E,WorldAPI<?>> wrapWorldField();
}