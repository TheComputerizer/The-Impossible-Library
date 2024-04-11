package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerStartTrackingEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;

public class PlayerStartTrackingEvent1_16_5 extends PlayerStartTrackingEventWrapper<StartTracking> {

    @Override
    protected EventFieldWrapper<StartTracking,EntityAPI<?>> wrapEntityField() {
        return wrapEntityGetter(StartTracking::getTarget);
    }

    @Override
    protected EventFieldWrapper<StartTracking,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(StartTracking::getPlayer);
    }
}