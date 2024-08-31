package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.AttachCapabilitiesEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

public class AttachCapabilitiesEventFabric extends AttachCapabilitiesEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
}