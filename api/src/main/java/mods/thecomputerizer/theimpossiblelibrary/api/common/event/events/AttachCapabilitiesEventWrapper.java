package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ATTACH_CAPABILITIES;

public abstract class AttachCapabilitiesEventWrapper<E> extends CommonEventWrapper<E> { //TODO Add API hooks for this

    protected AttachCapabilitiesEventWrapper() {
        super(ATTACH_CAPABILITIES);
    }

    @Override
    public void populate() {}
}