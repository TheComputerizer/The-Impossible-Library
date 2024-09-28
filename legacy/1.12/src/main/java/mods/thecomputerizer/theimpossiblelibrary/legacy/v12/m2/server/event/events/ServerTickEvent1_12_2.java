package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.events.ServerTickEventWrapper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventWrapper.ServerType.TICK_SERVER;

public class ServerTickEvent1_12_2 extends ServerTickEventWrapper<ServerTickEvent> {

    @SubscribeEvent
    public static void onEvent(ServerTickEvent event) {
        TICK_SERVER.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(ServerTickEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected TickPhase wrapTickPhase() {
        return Objects.nonNull(this.event) ? (event.phase==Phase.END ? END : START) : DEFAULT;
    }
}