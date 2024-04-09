package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonBlockStateEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.CROP_GROW_PRE;

public abstract class CropGrowPreEventWrapper<E> extends CommonBlockStateEventType<E> {

    protected CropGrowPreEventWrapper() {
        super(CROP_GROW_PRE);
    }
}