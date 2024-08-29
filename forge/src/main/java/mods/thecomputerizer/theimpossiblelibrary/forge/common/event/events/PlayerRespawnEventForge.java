package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerRespawnEventWrapper;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_RESPAWN;

public class PlayerRespawnEventForge extends PlayerRespawnEventWrapper<PlayerRespawnEvent> {
    
    @SubscribeEvent
    public static void onEvent(PlayerRespawnEvent event) {
        PLAYER_RESPAWN.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(PlayerRespawnEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<PlayerRespawnEvent,Boolean> wrapEndConqueredField() {
        return wrapGenericGetter(PlayerRespawnEvent::isEndConquered,false);
    }

    @Override protected EventFieldWrapper<PlayerRespawnEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(PlayerRespawnEvent::getPlayer);
    }
}