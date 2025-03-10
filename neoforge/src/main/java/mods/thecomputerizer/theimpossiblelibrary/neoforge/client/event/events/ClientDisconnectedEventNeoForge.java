package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientDisconnectedEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CLIENT_DISCONNECTED;

public class ClientDisconnectedEventNeoForge extends ClientDisconnectedEventWrapper<LoggingOut> {
    
    @SubscribeEvent
    public static void onEvent(LoggingOut event) {
        CLIENT_DISCONNECTED.invoke(event);
    }
    
    @Override public void setEvent(LoggingOut event) {
        super.setEvent(event);
    }
}