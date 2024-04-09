package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonLivingEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_JUMP;

public abstract class LivingJumpEventWrapper<E> extends CommonLivingEventType<E> {

    protected LivingJumpEventWrapper() {
        super(LIVING_JUMP);
    }
}