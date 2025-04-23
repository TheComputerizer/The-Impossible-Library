package mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.server.event.events;

import mods.thecomputerizer.theimpossiblelibrary.fabric.server.event.events.ServerTickEventFabric;
import net.fabricmc.fabric.api.event.Event;

import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.END_SERVER_TICK;

public class ServerTickEventFabric1_18_2 extends ServerTickEventFabric {
    
    @Override public Event<?> getEventInstance() {
        return END_SERVER_TICK;
    }
}