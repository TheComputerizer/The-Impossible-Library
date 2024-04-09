package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonLivingStackUseEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ITEM_USE_STOP;

public abstract class LivingItemUseStopEventWrapper<E> extends CommonLivingStackUseEventType<E> {

    protected LivingItemUseStopEventWrapper() {
        super(LIVING_ITEM_USE_STOP);
    }
}