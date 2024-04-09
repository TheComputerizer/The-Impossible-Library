package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerLoggedInEventWrapper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_LOGGED_IN;

public class PlayerLoggedInEventLegacy extends PlayerLoggedInEventWrapper<PlayerLoggedInEvent> {

    @SubscribeEvent
    public static void onEvent(PlayerLoggedInEvent event) {
        PLAYER_LOGGED_IN.invoke(event);
    }
}