package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractBlockEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_PUNCH_BLOCK;

public abstract class PlayerPunchBlockEventWrapper<E> extends CommonPlayerInteractBlockEventType<E> {

    protected PlayerPunchBlockEventWrapper() {
        super(PLAYER_PUNCH_BLOCK);
    }
}