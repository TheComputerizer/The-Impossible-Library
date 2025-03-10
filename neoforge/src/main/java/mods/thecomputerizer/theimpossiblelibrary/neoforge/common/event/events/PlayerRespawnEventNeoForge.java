package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerRespawnEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_RESPAWN;

public class PlayerRespawnEventNeoForge extends PlayerRespawnEventWrapper<PlayerRespawnEvent> {
    
    @SubscribeEvent
    public static void onEvent(PlayerRespawnEvent event) {
        PLAYER_RESPAWN.invoke(event);
    }
    
    @Override public void setEvent(PlayerRespawnEvent event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<PlayerRespawnEvent,Boolean> wrapEndConqueredField() {
        return wrapGenericGetter(PlayerRespawnEvent::isEndConquered,false);
    }

    @Override protected EventFieldWrapper<PlayerRespawnEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(PlayerRespawnEvent::getEntity);
    }
}