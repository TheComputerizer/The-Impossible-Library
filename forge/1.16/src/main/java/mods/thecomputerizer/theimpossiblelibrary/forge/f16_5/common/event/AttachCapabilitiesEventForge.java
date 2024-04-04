package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.AttachCapabilitiesEventWrapper;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class AttachCapabilitiesEventForge extends AttachCapabilitiesEventWrapper {

    private final AttachCapabilitiesEvent<?> event;

    public AttachCapabilitiesEventForge(AttachCapabilitiesEvent<?> event) {
        super(event.getObject());
        this.event = event;
    }
}
