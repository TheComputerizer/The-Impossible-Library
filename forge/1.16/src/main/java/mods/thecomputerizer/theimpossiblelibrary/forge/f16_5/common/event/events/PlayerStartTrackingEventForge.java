package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerStartTrackingEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;

public class PlayerStartTrackingEventForge extends PlayerStartTrackingEventWrapper<StartTracking> {

    @Override
    protected EventFieldWrapper<StartTracking,EntityAPI<?>> wrapEntityField() {
        return wrapEntityGetter(StartTracking::getTarget);
    }

    @Override
    protected EventFieldWrapper<StartTracking,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(StartTracking::getPlayer);
    }
}