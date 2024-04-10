package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerStopTrackingEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.StopTracking;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_STOP_TRACKING;

public class PlayerStopTrackingEventLegacy extends PlayerStopTrackingEventWrapper<StopTracking> {

    @SubscribeEvent
    public static void onEvent(StopTracking event) {
        PLAYER_STOP_TRACKING.invoke(event);
    }

    @Override
    protected EventFieldWrapper<StopTracking,EntityAPI<?>> wrapEntityField() {
        return wrapEntityGetter(StopTracking::getTarget);
    }

    @Override
    protected EventFieldWrapper<StopTracking,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(StopTracking::getEntityPlayer);
    }
}