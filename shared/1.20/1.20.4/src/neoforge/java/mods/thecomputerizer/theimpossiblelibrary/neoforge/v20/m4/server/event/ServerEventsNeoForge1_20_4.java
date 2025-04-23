package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.server.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.server.event.events.ServerTickEventNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.server.event.ServerEventsNeoForge1_20;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;
import static mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventWrapper.ServerType.TICK_SERVER;

public class ServerEventsNeoForge1_20_4 extends ServerEventsNeoForge1_20 {
    
    @Override public void defineEvents() {
        TICK_SERVER.setConnector(new ServerTickEventNeoForge1_20_4());
        super.defineEvents();
    }
    
    @Override public <R> Result getEventResult(R result) {
        return result==Result.DEFAULT ? DEFAULT : (result==Result.DENY ? DENY : ALLOW);
    }
    
    @SuppressWarnings("unchecked")
    @Override public Result setEventResult(Result result) {
        return result==DEFAULT ? Result.DEFAULT : (result==DENY ? Result.DENY : Result.ALLOW);
    }
}
