package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientConnectedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingIn;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CLIENT_CONNECTED;

public class ClientConnectedEventNeoForge extends ClientConnectedEventWrapper<LoggingIn> {
    
    @SubscribeEvent
    public static void onEvent(LoggingIn event) {
        CLIENT_CONNECTED.invoke(event);
    }
    
    @Override public void setEvent(LoggingIn event) {
        super.setEvent(event);
    }

    @Override protected EventFieldWrapper<LoggingIn,Boolean> wrapLocalField() {
        return wrapGenericGetter(event -> true,true);
    }

    @Override protected EventFieldWrapper<LoggingIn,String> wrapConnectionTypeField() {
        return wrapGenericGetter(event -> "","");
    }
}