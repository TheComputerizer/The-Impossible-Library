package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientConnectedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CLIENT_CONNECTED;

public class ClientConnectedEventForge extends ClientConnectedEventWrapper<LoggedInEvent> {
    
    @SubscribeEvent
    public static void onEvent(LoggedInEvent event) {
        CLIENT_CONNECTED.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LoggedInEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<LoggedInEvent,Boolean> wrapLocalField() {
        return wrapGenericGetter(event -> true,true);
    }

    @Override protected EventFieldWrapper<LoggedInEvent,String> wrapConnectionTypeField() {
        return wrapGenericGetter(event -> "","");
    }
}