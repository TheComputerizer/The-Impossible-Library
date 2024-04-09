package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingItemUseStopEventWrapper;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Stop;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ITEM_USE_STOP;

public class LivingItemUseStopEventLegacy extends LivingItemUseStopEventWrapper<Stop> {

    @SubscribeEvent
    public static void onEvent(Stop event) {
        LIVING_ITEM_USE_STOP.invoke(event);
    }
}