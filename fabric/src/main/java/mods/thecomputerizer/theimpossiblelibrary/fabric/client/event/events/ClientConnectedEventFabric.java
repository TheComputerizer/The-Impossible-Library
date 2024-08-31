package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientConnectedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.event.Event;

import static net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents.JOIN;

public class ClientConnectedEventFabric extends ClientConnectedEventWrapper<Object[]> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return JOIN;
    }

    @Override protected EventFieldWrapper<Object[],Boolean> wrapLocalField() {
        return wrapGenericGetter(event -> true,true);
    }

    @Override protected EventFieldWrapper<Object[],String> wrapConnectionTypeField() {
        return wrapGenericGetter(event -> "","");
    }
}