package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_ITEM;

public abstract class PlayerInteractItemEventWrapper<E> extends CommonPlayerInteractEventType<E> {

    protected PlayerInteractItemEventWrapper() {
        super(PLAYER_INTERACT_ITEM);
    }
}
