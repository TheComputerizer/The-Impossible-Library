package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_JUMP;

public abstract class LivingJumpEventWrapper<E> extends CommonEventWrapper<E> {

    protected LivingJumpEventWrapper() {
        super(LIVING_JUMP);
    }

    @Override
    protected void populate() {

    }
}