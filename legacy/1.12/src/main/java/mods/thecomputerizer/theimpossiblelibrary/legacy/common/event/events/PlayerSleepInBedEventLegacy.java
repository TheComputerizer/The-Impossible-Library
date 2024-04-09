package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerSleepInBedEventWrapper;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_SLEEP_IN_BED;

public class PlayerSleepInBedEventLegacy extends PlayerSleepInBedEventWrapper<PlayerSleepInBedEvent> {

    @SubscribeEvent
    public static void onEvent(PlayerSleepInBedEvent event) {
        PLAYER_SLEEP_IN_BED.invoke(event);
    }
}