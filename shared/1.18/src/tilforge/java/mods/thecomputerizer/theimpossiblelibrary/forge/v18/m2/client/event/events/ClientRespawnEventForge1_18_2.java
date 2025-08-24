package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.ClientRespawnEventForge;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.RespawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CLIENT_RESPAWN;

public class ClientRespawnEventForge1_18_2 extends ClientRespawnEventForge<RespawnEvent> {
    
    @SubscribeEvent
    public static void onEvent(RespawnEvent event) {
        CLIENT_RESPAWN.invoke(event);
    }
    
    @Override protected EventFieldWrapper<RespawnEvent,PlayerAPI<?,?>> wrapOldPlayerField() {
        return wrapPlayerGetter(RespawnEvent::getOldPlayer);
    }

    @Override protected EventFieldWrapper<RespawnEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(RespawnEvent::getNewPlayer);
    }
}