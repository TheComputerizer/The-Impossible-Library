package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.common.event.CommonEventsNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common.event.events.RegisterCommandsEventNeoForge1_20_4;
import net.neoforged.bus.api.Event;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;

public class CommonEventsNeoForge1_20_4 extends CommonEventsNeoForge1_20 {

    @Override public void defineEvents() {
        REGISTER_COMMANDS.setConnector(new RegisterCommandsEventNeoForge1_20_4());
        super.defineEvents();
    }
    
    @Override public <R> Result getEventResult(R result) {
        return result==Event.Result.DEFAULT ? DEFAULT : (result==Event.Result.DENY ? DENY : ALLOW);
    }
    
    @SuppressWarnings("unchecked")
    @Override public Event.Result setEventResult(Result result) {
        return result==DEFAULT ? Event.Result.DEFAULT : (result==DENY ? Event.Result.DENY : Event.Result.ALLOW);
    }
}