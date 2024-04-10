package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerFileEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_LOAD_FROM_FILE;

public abstract class PlayerLoadFromFileEventWrapper<E> extends CommonPlayerFileEventType<E> {

    protected PlayerLoadFromFileEventWrapper() {
        super(PLAYER_LOAD_FROM_FILE);
    }
}