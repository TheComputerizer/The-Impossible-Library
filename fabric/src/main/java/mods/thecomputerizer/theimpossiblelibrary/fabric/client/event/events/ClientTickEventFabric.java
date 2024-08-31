package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientTickEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.event.Event;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.END;

public class ClientTickEventFabric extends ClientTickEventWrapper<Object[]> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override
    protected TickPhase wrapTickPhase() {
        return Objects.nonNull(this.event) ? END : DEFAULT;
    }
}