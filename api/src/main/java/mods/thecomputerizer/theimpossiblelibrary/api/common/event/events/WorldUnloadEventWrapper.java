package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonWorldEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_UNLOAD;

public abstract class WorldUnloadEventWrapper<E> extends CommonWorldEventType<E> {

    protected WorldUnloadEventWrapper() {
        super(WORLD_UNLOAD);
    }
}