package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingItemUseFinishEventWrapper;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Finish;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ITEM_USE_FINISH;

public class LivingItemUseFinishEventLegacy extends LivingItemUseFinishEventWrapper<Finish> {

    @SubscribeEvent
    public static void onEvent(Finish event) {
        LIVING_ITEM_USE_FINISH.invoke(event);
    }
}