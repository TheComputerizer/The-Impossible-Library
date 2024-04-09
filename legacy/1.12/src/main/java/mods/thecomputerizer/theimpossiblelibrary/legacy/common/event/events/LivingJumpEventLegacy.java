package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingJumpEventWrapper;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_JUMP;

public class LivingJumpEventLegacy extends LivingJumpEventWrapper<LivingJumpEvent> {

    @SubscribeEvent
    public static void onEvent(LivingJumpEvent event) {
        LIVING_JUMP.invoke(event);
    }
}