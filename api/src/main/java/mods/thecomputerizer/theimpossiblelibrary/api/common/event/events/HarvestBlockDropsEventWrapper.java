package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_HARVEST;

public abstract class HarvestBlockDropsEventWrapper<E> extends CommonEventWrapper<E> {

    protected HarvestBlockDropsEventWrapper() {
        super(BLOCK_HARVEST);
    }

    @Override
    protected void populate() {

    }
}