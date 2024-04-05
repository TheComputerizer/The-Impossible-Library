package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_SET_TARGET;

public abstract class SetAttackTargetEventWrapper<E> extends CommonEventWrapper<E> {

    protected SetAttackTargetEventWrapper() {
        super(LIVING_SET_TARGET);
    }

    @Override
    protected void populate() {

    }
}