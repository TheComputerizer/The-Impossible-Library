package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingHealEventWrapper;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_HEAL;

public class LivingHealEventLegacy extends LivingHealEventWrapper<LivingHealEvent> {

    @SubscribeEvent
    public static void onEvent(LivingHealEvent event) {
        LIVING_HEAL.invoke(event);
    }
}