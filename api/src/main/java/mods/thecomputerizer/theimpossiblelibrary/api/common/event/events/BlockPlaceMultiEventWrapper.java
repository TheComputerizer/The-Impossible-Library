package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_PLACE_MULTI;

public abstract class BlockPlaceMultiEventWrapper<E> extends CommonEventWrapper<E> {

    protected BlockPlaceMultiEventWrapper() {
        super(BLOCK_PLACE_MULTI);
    }

    @Override
    protected void populate() {

    }
}