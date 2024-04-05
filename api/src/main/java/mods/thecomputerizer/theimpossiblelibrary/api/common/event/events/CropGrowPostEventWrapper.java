package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.CROP_GROW_POST;

public abstract class CropGrowPostEventWrapper<E> extends CommonEventWrapper<E> {

    protected CropGrowPostEventWrapper() {
        super(CROP_GROW_POST);
    }

    @Override
    protected void populate() {

    }
}