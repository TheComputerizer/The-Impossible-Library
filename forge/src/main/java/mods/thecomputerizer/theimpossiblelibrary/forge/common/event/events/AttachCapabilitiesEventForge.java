package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.AttachCapabilitiesEventWrapper;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ATTACH_CAPABILITIES;

public class AttachCapabilitiesEventForge extends AttachCapabilitiesEventWrapper<AttachCapabilitiesEvent<?>> {
    
    @SubscribeEvent
    public static void onEvent(AttachCapabilitiesEvent<?> event) {
        ATTACH_CAPABILITIES.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(AttachCapabilitiesEvent<?> event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}