package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_CREATE_FLUID;

public abstract class FluidCreateSourceEventWrapper<E> extends CommonEventWrapper<E> {

    protected FluidCreateSourceEventWrapper() {
        super(BLOCK_CREATE_FLUID);
    }

    @Override
    protected void populate() {

    }
}