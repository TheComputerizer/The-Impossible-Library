package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonLivingDamageEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_HURT;

public abstract class LivingHurtEventWrapper<E> extends CommonLivingDamageEventType<E> {

    protected LivingHurtEventWrapper() {
        super(LIVING_HURT);
    }
}