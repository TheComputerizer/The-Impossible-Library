package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_NAME_FORMAT;

public abstract class PlayerNameFormatEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerNameFormatEventWrapper() {
        super(PLAYER_NAME_FORMAT);
    }

    @Override
    protected void populate() {

    }
}