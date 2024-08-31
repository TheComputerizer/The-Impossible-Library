package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.fabric.util.CustomTickFabric;

public abstract class CommonEventsFabric implements CommonEventsAPI {
    
    @Override
    public void postCustomTick(CustomTick ticker) {
        CustomTickFabric.CUSTOM_TICK.invoker().onTick(ticker);
    }
    
    @Override
    public <E extends EventWrapper<?>> void register(E wrapper) {
        Class<?> wrapperClass = wrapper.getClass();
        ReflectionHelper.invokeStaticMethod(wrapperClass,"register",new Class<?>[]{wrapperClass}, wrapper);
    }
}