package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerStartTrackingEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.StartTracking;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_START_TRACKING;

public class PlayerStartTrackingEventNeoForge extends PlayerStartTrackingEventWrapper<StartTracking> {
    
    @SubscribeEvent
    public static void onEvent(StartTracking event) {
        PLAYER_START_TRACKING.invoke(event);
    }
    
    @Override public void setEvent(StartTracking event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<StartTracking,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(StartTracking::getTarget);
    }

    @Override protected EventFieldWrapper<StartTracking,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(StartTracking::getEntity);
    }
}