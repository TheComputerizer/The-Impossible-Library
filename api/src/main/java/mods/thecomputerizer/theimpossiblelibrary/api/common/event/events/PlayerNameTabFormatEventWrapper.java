package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_TAB_FORMAT;

public abstract class PlayerNameTabFormatEventWrapper<E> extends CommonPlayerEventType<E> {

    protected PlayerNameTabFormatEventWrapper() {
        super(PLAYER_TAB_FORMAT);
    }
}