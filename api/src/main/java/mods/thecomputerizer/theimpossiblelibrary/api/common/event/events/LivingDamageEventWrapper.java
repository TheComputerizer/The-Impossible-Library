package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonLivingDamageEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_DAMAGE;

public abstract class LivingDamageEventWrapper<E> extends CommonLivingDamageEventType<E> {

    protected LivingDamageEventWrapper() {
        super(LIVING_DAMAGE);
    }
}