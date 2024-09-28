package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonEntityEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ENTITY_JOIN_WORLD;

public abstract class EntityJoinWorldEventWrapper<E> extends CommonEntityEventType<E> {

    protected EventFieldWrapper<E,WorldAPI<?>> world;

    protected EntityJoinWorldEventWrapper() {
        super(ENTITY_JOIN_WORLD);
    }

    public WorldAPI<?> getWorld() {
        return this.world.get(this.event);
    }

    @Override protected void populate() {
        super.populate();
        this.world = wrapWorld();
    }

    protected abstract EventFieldWrapper<E,WorldAPI<?>> wrapWorld();
}