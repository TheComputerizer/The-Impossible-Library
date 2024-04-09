package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingItemUseStartEventWrapper;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Start;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ITEM_USE_START;

public class LivingItemUseStartEventLegacy extends LivingItemUseStartEventWrapper<Start> {

    @SubscribeEvent
    public static void onEvent(Start event) {
        LIVING_ITEM_USE_START.invoke(event);
    }
}