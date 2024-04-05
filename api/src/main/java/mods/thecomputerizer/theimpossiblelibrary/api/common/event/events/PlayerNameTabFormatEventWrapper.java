package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_TAB_FORMAT;

public abstract class PlayerNameTabFormatEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerNameTabFormatEventWrapper() {
        super(PLAYER_TAB_FORMAT);
    }

    @Override
    protected void populate() {

    }
}