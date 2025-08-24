package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.server.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.util.CustomTickNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.server.event.events.ServerTickEventNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.server.event.ServerEvents1_20;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;
import static mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventWrapper.ServerType.TICK_SERVER;
import static net.neoforged.neoforge.common.NeoForge.EVENT_BUS;

public class ServerEventsNeoForge1_20_4 extends ServerEvents1_20 {
    
    @Override public void defineEvents() {
        TICK_SERVER.setConnector(new ServerTickEventNeoForge1_20_4());
        super.defineEvents();
    }
    
    @Override public <R> Result getEventResult(R result) {
        return result==Result.DEFAULT ? DEFAULT : (result==Result.DENY ? DENY : ALLOW);
    }
    
    @Override public void postCustomTick(CustomTick ticker) {
        EVENT_BUS.post(new CustomTickNeoForge(ticker));
    }
    
    @Override public <E extends EventWrapper<?>> void register(E wrapper) {
        EVENT_BUS.register(wrapper.getClass());
    }
    
    @SuppressWarnings("unchecked")
    @Override public Result setEventResult(Result result) {
        return result==DEFAULT ? Result.DEFAULT : (result==DENY ? Result.DENY : Result.ALLOW);
    }
}
