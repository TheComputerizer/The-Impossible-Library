package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_GENERIC;

public abstract class RegistryEventWrapper<E> extends CommonEventWrapper<E> { //TODO Figure out generic registration stuff

    protected RegistryEventWrapper() {
        super(REGISTER_GENERIC);
    }

    @Override
    protected void populate() {

    }
}
