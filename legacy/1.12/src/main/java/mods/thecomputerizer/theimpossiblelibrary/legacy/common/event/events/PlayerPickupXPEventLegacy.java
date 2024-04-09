package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerPickupXPEventWrapper;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_XP_PICKUP;

public class PlayerPickupXPEventLegacy extends PlayerPickupXPEventWrapper<PlayerPickupXpEvent> {

    @SubscribeEvent
    public static void onEvent(PlayerPickupXpEvent event) {
        PLAYER_XP_PICKUP.invoke(event);
    }
}