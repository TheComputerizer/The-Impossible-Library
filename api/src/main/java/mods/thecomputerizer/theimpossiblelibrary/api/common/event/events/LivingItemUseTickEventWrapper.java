package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonLivingStackUseEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ITEM_USE_TICK;

public abstract class LivingItemUseTickEventWrapper<E> extends CommonLivingStackUseEventType<E> {

    protected LivingItemUseTickEventWrapper() {
        super(LIVING_ITEM_USE_TICK);
    }
}