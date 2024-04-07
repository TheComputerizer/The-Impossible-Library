package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_HEAL;

public abstract class LivingHealEventWrapper<E> extends CommonEventWrapper<E> {

    protected LivingHealEventWrapper() {
        super(LIVING_HEAL);
    }

    @Override
    protected void populate() {

    }
}