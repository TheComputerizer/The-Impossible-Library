package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientConnectedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CLIENT_CONNECTED;

public class ClientConnectedEventLegacy extends ClientConnectedEventWrapper<ClientConnectedToServerEvent> {

    @SubscribeEvent
    public static void onEvent(ClientConnectedToServerEvent event) {
        CLIENT_CONNECTED.invoke(event);
    }

    @Override
    protected EventFieldWrapper<ClientConnectedToServerEvent,Boolean> wrapLocalField() {
        return getFieldWrapperGetter(ClientConnectedToServerEvent::isLocal,false);
    }

    @Override
    protected EventFieldWrapper<ClientConnectedToServerEvent,String> wrapConnectionTypeField() {
        return getFieldWrapperGetter(ClientConnectedToServerEvent::getConnectionType,"");
    }
}