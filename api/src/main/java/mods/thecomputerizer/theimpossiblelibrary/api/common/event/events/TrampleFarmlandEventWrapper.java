package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonBlockStateEntityEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_TRAMPLE_FARMLAND;

public abstract class TrampleFarmlandEventWrapper<E> extends CommonBlockStateEntityEventType<E> {

    protected EventFieldWrapper<E,Float> fallDistance;

    protected TrampleFarmlandEventWrapper() {
        super(BLOCK_TRAMPLE_FARMLAND);
    }

    public float getFallDistance() {
        return this.fallDistance.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.fallDistance = wrapFallDistanceField();
    }

    protected abstract EventFieldWrapper<E,Float> wrapFallDistanceField();
}