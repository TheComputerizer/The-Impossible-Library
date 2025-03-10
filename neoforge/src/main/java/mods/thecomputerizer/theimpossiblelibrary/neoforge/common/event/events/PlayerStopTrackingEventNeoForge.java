package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerStopTrackingEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.StopTracking;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_STOP_TRACKING;

public class PlayerStopTrackingEventNeoForge extends PlayerStopTrackingEventWrapper<StopTracking> {
    
    @SubscribeEvent
    public static void onEvent(StopTracking event) {
        PLAYER_STOP_TRACKING.invoke(event);
    }
    
    @Override public void setEvent(StopTracking event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<StopTracking,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(StopTracking::getTarget);
    }

    @Override protected EventFieldWrapper<StopTracking,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(StopTracking::getEntity);
    }
}