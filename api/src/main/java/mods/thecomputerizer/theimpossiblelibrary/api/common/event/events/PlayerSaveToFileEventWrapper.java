package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerFileEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_SAVE_TO_FILE;

public abstract class PlayerSaveToFileEventWrapper<E> extends CommonPlayerFileEventType<E> {

    protected PlayerSaveToFileEventWrapper() {
        super(PLAYER_SAVE_TO_FILE);
    }
}