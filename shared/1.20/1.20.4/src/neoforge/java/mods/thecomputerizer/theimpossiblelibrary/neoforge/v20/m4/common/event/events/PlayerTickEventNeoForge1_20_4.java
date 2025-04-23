package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events.PlayerTickEventNeoForge;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.TickEvent.Phase;
import net.neoforged.neoforge.event.TickEvent.PlayerTickEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.TICK_PLAYER;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.END;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.START;

public class PlayerTickEventNeoForge1_20_4 extends PlayerTickEventNeoForge<PlayerTickEvent> {
    
    @SubscribeEvent
    public static void onEvent(PlayerTickEvent event) {
        TICK_PLAYER.invoke(event);
    }
    
    @Override public void setEvent(PlayerTickEvent event) {
        super.setEvent(event);
    }
    
    @Override protected TickPhase wrapTickPhase() {
        return Objects.nonNull(this.event) ? (event.phase==Phase.END ? END : START) : DEFAULT;
    }
}