package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.server.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.server.event.events.ServerTickEventNeoForge;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Pre;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.END;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.START;
import static mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventWrapper.ServerType.TICK_SERVER;

public class ServerTickEventNeoForge1_21 extends ServerTickEventNeoForge<ServerTickEvent> {
    
    @SubscribeEvent
    public static void onEvent(Post event) {
        TICK_SERVER.invoke(event);
    }
    
    @SubscribeEvent
    public static void onEvent(Pre event) {
        TICK_SERVER.invoke(event);
    }
    
    TickPhase phase;
    
    public void setEvent(ServerTickEvent event) {
        super.setEvent(event);
        this.phase = Objects.nonNull(event) ? (event instanceof Pre ? START : END) : DEFAULT;
    }
    
    @Override protected TickPhase wrapTickPhase() {
        return Objects.nonNull(this.phase) ? this.phase : DEFAULT;
    }
}
