package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingItemUseTickEventWrapper;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Tick;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ITEM_USE_TICK;

public class LivingItemUseTickEventLegacy extends LivingItemUseTickEventWrapper<Tick> {

    @SubscribeEvent
    public static void onEvent(Tick event) {
        LIVING_ITEM_USE_TICK.invoke(event);
    }
}