package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerStopTrackingEventWrapper;
import net.minecraftforge.event.entity.player.PlayerEvent.StopTracking;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_STOP_TRACKING;

public class PlayerStopTrackingEventForge extends PlayerStopTrackingEventWrapper<StopTracking> {
    
    @SubscribeEvent
    public static void onEvent(StopTracking event) {
        PLAYER_STOP_TRACKING.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(StopTracking event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<StopTracking,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(StopTracking::getTarget);
    }

    @Override
    protected EventFieldWrapper<StopTracking,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(StopTracking::getPlayer);
    }
}