package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_PUNCH_ITEM;

public abstract class PlayerPunchItemEventWrapper<E> extends CommonPlayerInteractEventType<E> {

    protected PlayerPunchItemEventWrapper() {
        super(PLAYER_PUNCH_ITEM);
    }
}