package mods.thecomputerizer.theimpossiblelibrary.forge.v21.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.ClientRespawnEventForge;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.Clone;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CLIENT_RESPAWN;

public class ClientRespawnEventForge1_21 extends ClientRespawnEventForge<Clone> {
    
    @SubscribeEvent
    public static void onEvent(Clone event) {
        CLIENT_RESPAWN.invoke(event);
    }
    
    @Override protected EventFieldWrapper<Clone,PlayerAPI<?,?>> wrapOldPlayerField() {
        return wrapPlayerGetter(Clone::getOldPlayer);
    }

    @Override protected EventFieldWrapper<Clone,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(Clone::getNewPlayer);
    }
}