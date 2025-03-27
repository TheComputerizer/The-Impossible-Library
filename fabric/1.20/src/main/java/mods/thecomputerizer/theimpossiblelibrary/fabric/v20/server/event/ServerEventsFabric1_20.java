package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.server.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CustomComonFabricEvents;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.server.event.events.ServerTickEventFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.server.event.ServerEvents1_20;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventWrapper.ServerType.TICK_SERVER;

public class ServerEventsFabric1_20 extends ServerEvents1_20 {
    
    @Override public void defineEvents() {
        TICK_SERVER.setConnector(new ServerTickEventFabric1_20());
        super.defineEvents();
    }
    
    @Override public <R> Result getEventResult(R result) {
        return DEFAULT;
    }
    
    @Override public void postCustomTick(CustomTick ticker) {
        CustomComonFabricEvents.CUSTOM_TICK.invoker().onTick(ticker);
    }
    
    @Override public <E extends EventWrapper<?>> void register(E wrapper) {
        TILRef.logInfo("Trying to register event wrapper {}", wrapper);
        ReflectionHelper.invokeMethod(wrapper.getClass(),"register",wrapper,new Class<?>[]{});
    }
    
    @SuppressWarnings("unchecked")
    @Override public Object setEventResult(Result result) {
        return null;
    }
}
