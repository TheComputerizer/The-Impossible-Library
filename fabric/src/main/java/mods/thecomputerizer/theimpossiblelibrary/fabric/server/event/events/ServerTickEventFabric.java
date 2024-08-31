package mods.thecomputerizer.theimpossiblelibrary.fabric.server.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.events.ServerTickEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.server.event.ServerFabricEvent;
import net.fabricmc.fabric.api.event.Event;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.END;
import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.END_SERVER_TICK;

public class ServerTickEventFabric extends ServerTickEventWrapper<Object[]> implements ServerFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return END_SERVER_TICK;
    }
    
    @Override protected TickPhase wrapTickPhase() {
        return Objects.nonNull(this.event) ? END : DEFAULT;
    }
}
