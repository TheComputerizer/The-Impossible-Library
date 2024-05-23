package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientDisconnectedEventWrapper;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggedOutEvent;

public class ClientDisconnectedEvent1_16_5 extends ClientDisconnectedEventWrapper<LoggedOutEvent> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LoggedOutEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}