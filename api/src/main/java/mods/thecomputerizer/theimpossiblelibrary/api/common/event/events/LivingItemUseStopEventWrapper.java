package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ITEM_USE_STOP;

public abstract class LivingItemUseStopEventWrapper<E> extends CommonEventWrapper<E> {

    protected LivingItemUseStopEventWrapper() {
        super(LIVING_ITEM_USE_STOP);
    }

    @Override
    protected void populate() {

    }
}