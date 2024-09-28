package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientDisconnectedEventWrapper;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggedOutEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CLIENT_DISCONNECTED;

public class ClientDisconnectedEventForge extends ClientDisconnectedEventWrapper<LoggedOutEvent> {
    
    @SubscribeEvent
    public static void onEvent(LoggedOutEvent event) {
        CLIENT_DISCONNECTED.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LoggedOutEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}