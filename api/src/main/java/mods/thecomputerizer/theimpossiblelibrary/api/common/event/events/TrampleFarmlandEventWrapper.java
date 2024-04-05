package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_TRAMPLE_FARMLAND;

public abstract class TrampleFarmlandEventWrapper<E> extends CommonEventWrapper<E> {

    protected TrampleFarmlandEventWrapper() {
        super(BLOCK_TRAMPLE_FARMLAND);
    }

    @Override
    protected void populate() {

    }
}