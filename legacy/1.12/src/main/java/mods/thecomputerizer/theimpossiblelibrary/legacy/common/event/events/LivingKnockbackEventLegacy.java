package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingKnockbackEventWrapper;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_KNOCKBACK;

public class LivingKnockbackEventLegacy extends LivingKnockbackEventWrapper<LivingKnockBackEvent> {

    @SubscribeEvent
    public static void onEvent(LivingKnockBackEvent event) {
        LIVING_KNOCKBACK.invoke(event);
    }
}