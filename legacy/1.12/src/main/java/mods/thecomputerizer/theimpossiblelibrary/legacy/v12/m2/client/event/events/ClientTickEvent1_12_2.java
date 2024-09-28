package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientTickEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.TICK_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.*;

public class ClientTickEvent1_12_2 extends ClientTickEventWrapper<ClientTickEvent> {

    @SubscribeEvent
    public static void onEvent(ClientTickEvent event) {
        TICK_CLIENT.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(ClientTickEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected TickPhase wrapTickPhase() {
        return Objects.nonNull(this.event) ? (event.phase==Phase.END ? END : START) : DEFAULT;
    }
}