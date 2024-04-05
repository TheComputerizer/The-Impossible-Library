package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_NOTIFY_NEIGHBOR;

public abstract class BlockNotifyNeighborEventWrapper<E> extends CommonEventWrapper<E> {

    protected BlockNotifyNeighborEventWrapper() {
        super(BLOCK_NOTIFY_NEIGHBOR);
    }

    @Override
    protected void populate() {

    }
}