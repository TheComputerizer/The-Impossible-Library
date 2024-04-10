package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonWorldEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_SAVE;

public abstract class WorldSaveEventWrapper<E> extends CommonWorldEventType<E> {

    protected WorldSaveEventWrapper() {
        super(WORLD_SAVE);
    }
}