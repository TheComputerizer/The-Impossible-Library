package mods.thecomputerizer.theimpossiblelibrary.fabric.v21.server.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v21.server.event.events.ServerTickEventFabric1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.server.event.ServerEvents1_21;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventWrapper.ServerType.TICK_SERVER;
import static mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CustomComonFabricEvents.CUSTOM_TICK;

public class ServerEventsFabric1_21 extends ServerEvents1_21 {
    
    @Override public void defineEvents() {
        TICK_SERVER.setConnector(new ServerTickEventFabric1_21());
        super.defineEvents();
    }
    
    @Override public <R> Result getEventResult(R result) {
        return DEFAULT;
    }
    
    @Override public void postCustomTick(CustomTick ticker) {
        CUSTOM_TICK.invoker().onTick(ticker);
    }
    
    @Override public <E extends EventWrapper<?>> void register(E wrapper) {
        Hacks.invoke(wrapper,"register");
    }
    
    @SuppressWarnings("unchecked")
    @Override public Object setEventResult(Result result) {
        return null;
    }
}
