package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientConnectedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CLIENT_CONNECTED;

public class ClientConnectedEvent1_12_2 extends ClientConnectedEventWrapper<ClientConnectedToServerEvent> {

    @SubscribeEvent
    public static void onEvent(ClientConnectedToServerEvent event) {
        CLIENT_CONNECTED.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(ClientConnectedToServerEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<ClientConnectedToServerEvent,Boolean> wrapLocalField() {
        return wrapGenericGetter(ClientConnectedToServerEvent::isLocal,false);
    }

    @Override protected EventFieldWrapper<ClientConnectedToServerEvent,String> wrapConnectionTypeField() {
        return wrapGenericGetter(ClientConnectedToServerEvent::getConnectionType,"");
    }
}