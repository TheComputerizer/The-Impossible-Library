package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_INTERACT;

public abstract class BlockInteractEventWrapper<E> extends CommonEventWrapper<E> {

    protected BlockInteractEventWrapper() {
        super(BLOCK_INTERACT);
    }

    @Override
    protected void populate() {

    }
}