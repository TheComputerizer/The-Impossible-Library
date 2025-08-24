package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.server.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.server.event.events.ServerTickEventNeoForge;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.TickEvent.Phase;
import net.neoforged.neoforge.event.TickEvent.ServerTickEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.END;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.START;
import static mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventWrapper.ServerType.TICK_SERVER;

public class ServerTickEventNeoForge1_20_4 extends ServerTickEventNeoForge<ServerTickEvent> {
    
    @SubscribeEvent
    public static void onEvent(ServerTickEvent event) {
        TICK_SERVER.invoke(event);
    }
    
    @Override public void setEvent(ServerTickEvent event) {
        super.setEvent(event);
    }
    
    @Override protected TickPhase wrapTickPhase() {
        return Objects.nonNull(this.event) ? (event.phase==Phase.END ? END : START) : DEFAULT;
    }
}
