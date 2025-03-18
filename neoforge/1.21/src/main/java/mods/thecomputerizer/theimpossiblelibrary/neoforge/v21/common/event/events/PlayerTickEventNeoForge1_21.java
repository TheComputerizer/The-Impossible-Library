package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events.PlayerTickEventNeoForge;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Pre;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.TICK_PLAYER;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.END;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.START;

public class PlayerTickEventNeoForge1_21 extends PlayerTickEventNeoForge<PlayerTickEvent> {
    
    @SubscribeEvent
    public static void onEvent(Post event) {
        TICK_PLAYER.invoke(event);
    }
    
    @SubscribeEvent
    public static void onEvent(Pre event) {
        TICK_PLAYER.invoke(event);
    }
    
    TickPhase phase;
    
    @Override public void setEvent(PlayerTickEvent event) {
        super.setEvent(event);
        this.phase = Objects.nonNull(event) ? (event instanceof Pre ? START : END) : DEFAULT;
    }
    
    @Override protected TickPhase wrapTickPhase() {
        return Objects.nonNull(this.phase) ? this.phase : DEFAULT;
    }
}