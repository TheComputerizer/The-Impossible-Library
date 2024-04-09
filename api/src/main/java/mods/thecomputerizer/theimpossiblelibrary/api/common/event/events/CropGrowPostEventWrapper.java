package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonBlockStateEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockStateAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.CROP_GROW_POST;

public abstract class CropGrowPostEventWrapper<E> extends CommonBlockStateEventType<E> {

    protected EventFieldWrapper<E,BlockStateAPI<?>> originalState;

    protected CropGrowPostEventWrapper() {
        super(CROP_GROW_POST);
    }

    public BlockStateAPI<?> getOriginalState() {
        return this.originalState.get(this.event);
    }

    @Override
    protected void populate() {
        super.populate();
        this.originalState = wrapOriginalStateField();
    }

    protected abstract EventFieldWrapper<E,BlockStateAPI<?>> wrapOriginalStateField();
}