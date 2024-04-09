package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerStopTrackingEventWrapper;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StopTracking;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_STOP_TRACKING;

public class PlayerStopTrackingEventLegacy extends PlayerStopTrackingEventWrapper<StopTracking> {

    @SubscribeEvent
    public static void onEvent(PlayerEvent.StartTracking event) {
        PLAYER_STOP_TRACKING.invoke(event);
    }
}