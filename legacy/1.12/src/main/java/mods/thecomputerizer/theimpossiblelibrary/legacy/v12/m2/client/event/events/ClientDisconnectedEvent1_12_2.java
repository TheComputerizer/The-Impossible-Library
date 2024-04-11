package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientDisconnectedEventWrapper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CLIENT_DISCONNECTED;

public class ClientDisconnectedEvent1_12_2 extends ClientDisconnectedEventWrapper<ClientDisconnectionFromServerEvent> {

    @SubscribeEvent
    public static void onEvent(ClientDisconnectionFromServerEvent event) {
        CLIENT_DISCONNECTED.invoke(event);
    }
}