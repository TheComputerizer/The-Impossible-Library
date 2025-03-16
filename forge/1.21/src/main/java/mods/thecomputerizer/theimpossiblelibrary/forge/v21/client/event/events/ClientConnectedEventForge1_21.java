package mods.thecomputerizer.theimpossiblelibrary.forge.v21.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientConnectedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggingIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CLIENT_CONNECTED;

public class ClientConnectedEventForge1_21 extends ClientConnectedEventWrapper<LoggingIn> {
    
    @SubscribeEvent
    public static void onEvent(LoggingIn event) {
        CLIENT_CONNECTED.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LoggingIn event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<LoggingIn,Boolean> wrapLocalField() {
        return wrapGenericGetter(event -> true,true);
    }

    @Override protected EventFieldWrapper<LoggingIn,String> wrapConnectionTypeField() {
        return wrapGenericGetter(event -> "","");
    }
}