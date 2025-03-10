package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.AttachCapabilitiesEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ATTACH_CAPABILITIES;

//TODO
public class AttachCapabilitiesEventNeoForge extends AttachCapabilitiesEventWrapper<Object> {
    
    public static void onEvent(Object event) {
        ATTACH_CAPABILITIES.invoke(event);
    }
    
    @Override public void setEvent(Object event) {
        super.setEvent(event);
    }
}