package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_PUNCH_BLOCK;

public abstract class PlayerPunchBlockEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerPunchBlockEventWrapper() {
        super(PLAYER_PUNCH_BLOCK);
    }

    @Override
    protected void populate() {

    }
}