package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerLoggedInEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_LOGGED_IN;

public class PlayerLoggedInEventNeoForge extends PlayerLoggedInEventWrapper<PlayerLoggedInEvent> {
    
    @SubscribeEvent
    public static void onEvent(PlayerLoggedInEvent event) {
        PLAYER_LOGGED_IN.invoke(event);
    }
    
    @Override public void setEvent(PlayerLoggedInEvent event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<PlayerLoggedInEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(PlayerLoggedInEvent::getEntity);
    }
}