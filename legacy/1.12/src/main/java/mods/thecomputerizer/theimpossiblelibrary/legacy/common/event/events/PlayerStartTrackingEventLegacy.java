package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerStartTrackingEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_START_TRACKING;

public class PlayerStartTrackingEventLegacy extends PlayerStartTrackingEventWrapper<StartTracking> {

    @SubscribeEvent
    public static void onEvent(StartTracking event) {
        PLAYER_START_TRACKING.invoke(event);
    }

    @Override
    protected EventFieldWrapper<StartTracking,EntityAPI<?>> wrapEntityField() {
        return wrapEntityGetter(StartTracking::getTarget);
    }

    @Override
    protected EventFieldWrapper<StartTracking,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(StartTracking::getEntityPlayer);
    }
}