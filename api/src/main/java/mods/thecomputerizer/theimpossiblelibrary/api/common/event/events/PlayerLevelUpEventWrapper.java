package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_XP_LEVEL_CHANGE;

public abstract class PlayerLevelUpEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerLevelUpEventWrapper() {
        super(PLAYER_XP_LEVEL_CHANGE);
    }

    @Override
    protected void populate() {

    }
}