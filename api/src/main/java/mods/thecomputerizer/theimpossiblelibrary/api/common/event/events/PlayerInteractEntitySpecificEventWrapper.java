package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_ENTITY_AT;

public abstract class PlayerInteractEntitySpecificEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerInteractEntitySpecificEventWrapper() {
        super(PLAYER_INTERACT_ENTITY_AT);
    }

    @Override
    protected void populate() {

    }
}