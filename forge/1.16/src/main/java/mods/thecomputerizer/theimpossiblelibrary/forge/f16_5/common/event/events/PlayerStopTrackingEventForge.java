package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerStopTrackingEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.StopTracking;

public class PlayerStopTrackingEventForge extends PlayerStopTrackingEventWrapper<StopTracking> {

    @Override
    protected EventFieldWrapper<StopTracking,EntityAPI<?>> wrapEntityField() {
        return wrapEntityGetter(StopTracking::getTarget);
    }

    @Override
    protected EventFieldWrapper<StopTracking,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(StopTracking::getPlayer);
    }
}