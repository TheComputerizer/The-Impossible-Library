package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonBlockStateEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_CREATE_FLUID;

public abstract class FluidCreateSourceEventWrapper<E> extends CommonBlockStateEventType<E> {

    protected FluidCreateSourceEventWrapper() {
        super(BLOCK_CREATE_FLUID);
    }
}