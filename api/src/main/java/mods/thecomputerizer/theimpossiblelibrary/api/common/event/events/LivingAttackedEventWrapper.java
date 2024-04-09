package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonLivingDamageEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ATTACKED;

public abstract class LivingAttackedEventWrapper<E> extends CommonLivingDamageEventType<E> {

    protected LivingAttackedEventWrapper() {
        super(LIVING_ATTACKED);
    }
}
