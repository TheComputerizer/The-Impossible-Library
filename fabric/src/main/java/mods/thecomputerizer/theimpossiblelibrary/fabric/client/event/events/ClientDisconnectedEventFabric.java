package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientDisconnectedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.event.Event;

import static net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents.DISCONNECT;

public class ClientDisconnectedEventFabric extends ClientDisconnectedEventWrapper<Object[]> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return DISCONNECT;
    }
}