package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractBlockEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_BLOCK;

public abstract class PlayerInteractBlockEventWrapper<E> extends CommonPlayerInteractBlockEventType<E> {

    protected PlayerInteractBlockEventWrapper() {
        super(PLAYER_INTERACT_BLOCK);
    }
}