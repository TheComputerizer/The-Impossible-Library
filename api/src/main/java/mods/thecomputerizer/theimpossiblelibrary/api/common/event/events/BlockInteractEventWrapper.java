package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonBlockStatePlayerEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_INTERACT;

public abstract class BlockInteractEventWrapper<E> extends CommonBlockStatePlayerEventType<E> {

    protected BlockInteractEventWrapper() {
        super(BLOCK_INTERACT);
    }
}