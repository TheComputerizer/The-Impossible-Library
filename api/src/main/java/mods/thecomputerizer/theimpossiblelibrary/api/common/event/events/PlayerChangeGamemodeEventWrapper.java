package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_CHANGE_GAMEMODE;

public abstract class PlayerChangeGamemodeEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerChangeGamemodeEventWrapper() {
        super(PLAYER_CHANGE_GAMEMODE);
    }

    @Override
    protected void populate() {

    }
}
