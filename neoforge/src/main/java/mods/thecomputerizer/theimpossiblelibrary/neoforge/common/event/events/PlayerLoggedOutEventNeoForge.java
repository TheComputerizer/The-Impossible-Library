package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerLoggedOutEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_LOGGED_OUT;

public class PlayerLoggedOutEventNeoForge extends PlayerLoggedOutEventWrapper<PlayerLoggedOutEvent> {
    
    @SubscribeEvent
    public static void onEvent(PlayerLoggedOutEvent event) {
        PLAYER_LOGGED_OUT.invoke(event);
    }
    
    
    @Override public void setEvent(PlayerLoggedOutEvent event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<PlayerLoggedOutEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(PlayerLoggedOutEvent::getEntity);
    }
}