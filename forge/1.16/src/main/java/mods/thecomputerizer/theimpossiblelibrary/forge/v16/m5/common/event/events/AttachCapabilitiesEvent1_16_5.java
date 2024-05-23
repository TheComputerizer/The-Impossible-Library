package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.AttachCapabilitiesEventWrapper;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class AttachCapabilitiesEvent1_16_5 extends AttachCapabilitiesEventWrapper<AttachCapabilitiesEvent<?>> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(AttachCapabilitiesEvent<?> event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}