package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerStopTrackingEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.StopTracking;

public class PlayerStopTrackingEvent1_16_5 extends PlayerStopTrackingEventWrapper<StopTracking> {

    @Override
    protected EventFieldWrapper<StopTracking,EntityAPI<?>> wrapEntityField() {
        return wrapEntityGetter(StopTracking::getTarget);
    }

    @Override
    protected EventFieldWrapper<StopTracking,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(StopTracking::getPlayer);
    }
}