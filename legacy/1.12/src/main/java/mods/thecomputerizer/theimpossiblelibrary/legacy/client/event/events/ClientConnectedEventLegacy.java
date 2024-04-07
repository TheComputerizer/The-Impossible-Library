package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientConnectedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;

public class ClientConnectedEventLegacy extends ClientConnectedEventWrapper<ClientConnectedToServerEvent> {

    @Override
    protected EventFieldWrapper<ClientConnectedToServerEvent,Boolean> wrapLocalField() {
        return getFieldWrapperGetter(ClientConnectedToServerEvent::isLocal,false);
    }

    @Override
    protected EventFieldWrapper<ClientConnectedToServerEvent,String> wrapConnectionTypeField() {
        return getFieldWrapperGetter(ClientConnectedToServerEvent::getConnectionType,"");
    }
}