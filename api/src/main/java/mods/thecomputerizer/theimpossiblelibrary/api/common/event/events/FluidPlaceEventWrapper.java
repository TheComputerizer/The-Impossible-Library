package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonBlockStateEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_PLACE_FLUID;

public abstract class FluidPlaceEventWrapper<E> extends CommonBlockStateEventType<E> {

    protected EventFieldWrapper<E,BlockPosAPI<?>> fluidPos;
    protected EventFieldWrapper<E,BlockStateAPI<?>> newState;
    protected EventFieldWrapper<E,BlockStateAPI<?>> originalState;

    protected FluidPlaceEventWrapper() {
        super(BLOCK_PLACE_FLUID);
    }

    public BlockPosAPI<?> getFluidPos() {
        return this.fluidPos.get(this.event);
    }

    public BlockStateAPI<?> getNewState() {
        return this.newState.get(this.event);
    }

    public BlockStateAPI<?> getOriginalState() {
        return this.originalState.get(this.event);
    }

    @Override protected void populate() {
        super.populate();
        this.fluidPos = wrapFluidPosField();
        this.newState = wrapNewStateField();
        this.originalState = wrapOriginalStateField();
    }

    public void setNewState(BlockStateAPI<?> state) {
        this.newState.set(this.event,state);
    }

    protected abstract EventFieldWrapper<E,BlockPosAPI<?>> wrapFluidPosField();
    protected abstract EventFieldWrapper<E,BlockStateAPI<?>> wrapNewStateField();
    protected abstract EventFieldWrapper<E,BlockStateAPI<?>> wrapOriginalStateField();
}