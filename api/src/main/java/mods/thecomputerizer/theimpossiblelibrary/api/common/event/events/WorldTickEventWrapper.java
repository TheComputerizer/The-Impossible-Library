package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.TICK_WORLD;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickType.WORLD;

public abstract class WorldTickEventWrapper<E> extends CommonTickableEventType<E> {

    protected WorldTickEventWrapper() {
        super(TICK_WORLD);
    }
    
    public abstract WorldAPI<?> getWorld();

    @Override protected TickType wrapTickType() {
        return WORLD;
    }
}