package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.server.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.util.CustomTickNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.server.event.events.ServerTickEventNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.server.event.ServerEvents1_20;
import net.neoforged.neoforge.common.util.TriState;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;
import static mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventWrapper.ServerType.TICK_SERVER;
import static net.neoforged.neoforge.common.NeoForge.EVENT_BUS;

public class ServerEventsNeoForge1_20_6 extends ServerEvents1_20 {
    
    @Override public void defineEvents() {
        TICK_SERVER.setConnector(new ServerTickEventNeoForge1_20_6());
        super.defineEvents();
    }
    
    @Override public <R> Result getEventResult(R result) {
        return result==TriState.DEFAULT ? DEFAULT : (result==TriState.FALSE ? DENY : ALLOW);
    }
    
    @Override public void postCustomTick(CustomTick ticker) {
        EVENT_BUS.post(new CustomTickNeoForge(ticker));
    }
    
    @Override public <E extends EventWrapper<?>> void register(E wrapper) {
        EVENT_BUS.register(wrapper.getClass());
    }
    
    @SuppressWarnings("unchecked")
    @Override public TriState setEventResult(Result result) {
        return result==DEFAULT ? TriState.DEFAULT : (result==DENY ? TriState.FALSE : TriState.TRUE);
    }
}
