package mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.server.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CustomFabricEvents;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.server.event.events.ServerTickEventFabric1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.server.event.ServerEvents1_18_2;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventWrapper.ServerType.TICK_SERVER;

public class ServerEventsFabric1_18_2 extends ServerEvents1_18_2 {
    
    @Override public void defineEvents() {
        TICK_SERVER.setConnector(new ServerTickEventFabric1_18_2());
        super.defineEvents();
    }
    
    @Override public <R> Result getEventResult(R result) {
        return DEFAULT;
    }
    
    @Override public void postCustomTick(CustomTick ticker) {
        CustomFabricEvents.CUSTOM_TICK.invoker().onTick(ticker);
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
