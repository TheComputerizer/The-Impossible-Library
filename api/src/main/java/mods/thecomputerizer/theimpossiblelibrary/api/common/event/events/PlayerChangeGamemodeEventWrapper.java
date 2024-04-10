package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_CHANGE_GAMEMODE;

public abstract class PlayerChangeGamemodeEventWrapper<E> extends CommonPlayerEventType<E> {

    protected PlayerChangeGamemodeEventWrapper() {
        super(PLAYER_CHANGE_GAMEMODE);
    }
}
