package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_PLACE_FLUID;

public abstract class FluidPlaceEventWrapper<E> extends CommonEventWrapper<E> {

    protected FluidPlaceEventWrapper() {
        super(BLOCK_PLACE_FLUID);
    }

    @Override
    protected void populate() {

    }
}