package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.AttachCapabilitiesEventWrapper;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class AttachCapabilitiesEventLegacy<O> extends AttachCapabilitiesEventWrapper<O> {

    private final AttachCapabilitiesEvent<?> event;

    @SuppressWarnings("unchecked")
    public AttachCapabilitiesEventLegacy(AttachCapabilitiesEvent<?> event) {
        super((O)event.getObject());
        this.event = event;
    }
}
