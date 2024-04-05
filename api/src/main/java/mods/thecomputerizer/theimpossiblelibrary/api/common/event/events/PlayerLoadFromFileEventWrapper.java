package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_LOAD_FROM_FILE;

public abstract class PlayerLoadFromFileEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerLoadFromFileEventWrapper() {
        super(PLAYER_LOAD_FROM_FILE);
    }

    @Override
    protected void populate() {

    }
}