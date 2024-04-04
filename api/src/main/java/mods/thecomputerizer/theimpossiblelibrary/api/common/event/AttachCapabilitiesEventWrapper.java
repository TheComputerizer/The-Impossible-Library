package mods.thecomputerizer.theimpossiblelibrary.api.common.event;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ATTACH_CAPABILITIES;

public abstract class AttachCapabilitiesEventWrapper<E> extends CommonEventWrapper<E> { //TODO Add API hooks for this

    protected AttachCapabilitiesEventWrapper() {
        super(ATTACH_CAPABILITIES);
    }
}