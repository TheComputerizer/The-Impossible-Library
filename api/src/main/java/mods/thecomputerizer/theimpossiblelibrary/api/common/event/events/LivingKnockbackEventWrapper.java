package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_KNOCKBACK;

public abstract class LivingKnockbackEventWrapper<E> extends CommonEventWrapper<E> {

    protected LivingKnockbackEventWrapper() {
        super(LIVING_KNOCKBACK);
    }

    @Override
    protected void populate() {

    }
}