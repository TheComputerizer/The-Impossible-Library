package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonLivingDamageEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_DEATH;

public abstract class LivingDeathEventWrapper<E> extends CommonLivingDamageEventType<E> {

    protected LivingDeathEventWrapper() {
        super(LIVING_DEATH);
    }
}