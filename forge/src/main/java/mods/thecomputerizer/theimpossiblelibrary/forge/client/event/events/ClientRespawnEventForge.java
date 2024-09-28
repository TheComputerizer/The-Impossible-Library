package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientRespawnEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.RespawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CLIENT_RESPAWN;

public class ClientRespawnEventForge extends ClientRespawnEventWrapper<RespawnEvent> {
    
    @SubscribeEvent
    public static void onEvent(RespawnEvent event) {
        CLIENT_RESPAWN.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(RespawnEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<RespawnEvent,PlayerAPI<?,?>> wrapOldPlayerField() {
        return wrapPlayerGetter(RespawnEvent::getOldPlayer);
    }

    @Override protected EventFieldWrapper<RespawnEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(RespawnEvent::getNewPlayer);
    }
}