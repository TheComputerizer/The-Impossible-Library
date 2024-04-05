package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_UPDATE;

public abstract class LivingUpdateEventWrapper<E> extends CommonEventWrapper<E> {

    protected LivingUpdateEventWrapper() {
        super(LIVING_UPDATE);
    }

    @Override
    protected void populate() {

    }
}