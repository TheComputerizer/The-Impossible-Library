package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.AttachCapabilitiesEventWrapper;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class AttachCapabilitiesEventForge<O> extends AttachCapabilitiesEventWrapper<O> {

    private final AttachCapabilitiesEvent<?> event;

    @SuppressWarnings("unchecked")
    public AttachCapabilitiesEventForge(AttachCapabilitiesEvent<?> event) {
        super((O)event.getObject());
        this.event = event;
    }
}
