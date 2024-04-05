package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_CHANGED_DIMENSIONS;

public abstract class PlayerChangedDimensionsEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerChangedDimensionsEventWrapper() {
        super(PLAYER_CHANGED_DIMENSIONS);
    }

    @Override
    protected void populate() {

    }
}