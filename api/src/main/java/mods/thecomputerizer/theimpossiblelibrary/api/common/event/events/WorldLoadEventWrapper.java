package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonWorldEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_LOAD;

public abstract class WorldLoadEventWrapper<E> extends CommonWorldEventType<E> {

    protected WorldLoadEventWrapper() {
        super(WORLD_LOAD);
    }
}