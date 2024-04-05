package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_RESPAWN;

public abstract class PlayerRespawnEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerRespawnEventWrapper() {
        super(PLAYER_RESPAWN);
    }

    @Override
    protected void populate() {

    }
}