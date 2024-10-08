package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.SoundSetupEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.event.Event;

public class SoundSetupEventFabric extends SoundSetupEventWrapper<Object[]> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
}