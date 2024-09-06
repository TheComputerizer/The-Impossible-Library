package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.CustomTickEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CustomFabricEvents.CUSTOM_TICK;

public class CustomTickEventFabric extends CustomTickEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return CUSTOM_TICK;
    }
    
    @Override protected CustomTick wrapTicker() {
        return Objects.nonNull(this.event) ? (CustomTick)this.event[0] : null;
    }
}