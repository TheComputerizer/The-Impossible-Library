package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.client.event.ClientEventsNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.client.event.events.ClientTickEventNeoForge1_20_4;
import net.neoforged.bus.api.Event;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.TICK_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;

public class ClientEventsNeoForge1_20_4 extends ClientEventsNeoForge1_20 {
    
    @Override public void defineEvents() {
        TICK_CLIENT.setConnector(new ClientTickEventNeoForge1_20_4());
        super.defineEvents();
    }
    
    @Override public <R> Result getEventResult(R result) {
        return result==net.neoforged.bus.api.Event.Result.DEFAULT ? DEFAULT : (result==net.neoforged.bus.api.Event.Result.DENY ? DENY : ALLOW);
    }
    
    @SuppressWarnings("unchecked")
    @Override public net.neoforged.bus.api.Event.Result setEventResult(Result result) {
        return result==DEFAULT ? net.neoforged.bus.api.Event.Result.DEFAULT : (result==DENY ? net.neoforged.bus.api.Event.Result.DENY : Event.Result.ALLOW);
    }
}