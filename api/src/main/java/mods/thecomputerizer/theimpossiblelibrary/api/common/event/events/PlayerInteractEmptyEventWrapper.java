package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_EMPTY;

public abstract class PlayerInteractEmptyEventWrapper<E> extends CommonPlayerInteractEventType<E> {

    protected PlayerInteractEmptyEventWrapper() {
        super(PLAYER_INTERACT_EMPTY);
    }
}