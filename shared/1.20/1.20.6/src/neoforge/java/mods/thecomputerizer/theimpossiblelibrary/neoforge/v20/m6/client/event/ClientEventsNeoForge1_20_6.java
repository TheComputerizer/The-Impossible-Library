package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.client.event.ClientEventsNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.client.event.events.ClientTickEventNeoForge1_20_6;
import net.neoforged.neoforge.common.util.TriState;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.TICK_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;

public class ClientEventsNeoForge1_20_6 extends ClientEventsNeoForge1_20 {
    
    @Override public void defineEvents() {
        TICK_CLIENT.setConnector(new ClientTickEventNeoForge1_20_6());
        super.defineEvents();
    }
    
    @Override public <R> Result getEventResult(R result) {
        return result==TriState.DEFAULT ? DEFAULT : (result==TriState.FALSE ? DENY : ALLOW);
    }
    
    @SuppressWarnings("unchecked")
    @Override public TriState setEventResult(Result result) {
        return result==DEFAULT ? TriState.DEFAULT : (result==DENY ? TriState.FALSE : TriState.TRUE);
    }
}