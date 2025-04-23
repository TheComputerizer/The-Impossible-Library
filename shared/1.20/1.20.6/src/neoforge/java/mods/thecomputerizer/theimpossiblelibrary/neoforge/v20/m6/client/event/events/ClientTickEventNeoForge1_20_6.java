package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events.ClientTickEventNeoForge;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.ClientTickEvent.Pre;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.TICK_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.END;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.START;

public class ClientTickEventNeoForge1_20_6 extends ClientTickEventNeoForge<ClientTickEvent> {
    
    @SubscribeEvent
    public static void onEvent(Post event) {
        TICK_CLIENT.invoke(event);
    }
    
    @SubscribeEvent
    public static void onEvent(Pre event) {
        TICK_CLIENT.invoke(event);
    }
    
    TickPhase phase;
    
    public void setEvent(ClientTickEvent event) {
        super.setEvent(event);
        this.phase = Objects.nonNull(event) ? (event instanceof Pre ? START : END) : DEFAULT;
    }
    
    @Override protected TickPhase wrapTickPhase() {
        return Objects.nonNull(this.phase) ? this.phase : DEFAULT;
    }
}