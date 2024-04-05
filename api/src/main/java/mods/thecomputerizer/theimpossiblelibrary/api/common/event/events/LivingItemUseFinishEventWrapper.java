package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ITEM_USE_FINISH;

public abstract class LivingItemUseFinishEventWrapper<E> extends CommonEventWrapper<E> {

    protected LivingItemUseFinishEventWrapper() {
        super(LIVING_ITEM_USE_FINISH);
    }

    @Override
    protected void populate() {

    }
}