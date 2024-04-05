package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ITEM_USE_START;

public abstract class LivingItemUseStartEventWrapper<E> extends CommonEventWrapper<E> {

    protected LivingItemUseStartEventWrapper() {
        super(LIVING_ITEM_USE_START);
    }

    @Override
    protected void populate() {

    }
}