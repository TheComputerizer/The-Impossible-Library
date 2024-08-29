package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerStartTrackingEventWrapper;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_START_TRACKING;

public class PlayerStartTrackingEventForge extends PlayerStartTrackingEventWrapper<StartTracking> {
    
    @SubscribeEvent
    public static void onEvent(StartTracking event) {
        PLAYER_START_TRACKING.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(StartTracking event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<StartTracking,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(StartTracking::getTarget);
    }

    @Override protected EventFieldWrapper<StartTracking,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(StartTracking::getPlayer);
    }
}