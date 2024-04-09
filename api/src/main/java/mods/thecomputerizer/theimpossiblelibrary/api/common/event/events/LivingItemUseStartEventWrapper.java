package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonLivingStackUseEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ITEM_USE_START;

public abstract class LivingItemUseStartEventWrapper<E> extends CommonLivingStackUseEventType<E> {

    protected LivingItemUseStartEventWrapper() {
        super(LIVING_ITEM_USE_START);
    }
}