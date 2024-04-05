package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_SAVE_TO_FILE;

public abstract class PlayerSaveToFileEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerSaveToFileEventWrapper() {
        super(PLAYER_SAVE_TO_FILE);
    }

    @Override
    protected void populate() {

    }
}