package mods.thecomputerizer.theimpossiblelibrary.forge.v20.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientDisconnectedEventWrapper;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CLIENT_DISCONNECTED;

public class ClientDisconnectedEventForge1_20 extends ClientDisconnectedEventWrapper<LoggingOut> {
    
    @SubscribeEvent
    public static void onEvent(LoggingOut event) {
        CLIENT_DISCONNECTED.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LoggingOut event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}