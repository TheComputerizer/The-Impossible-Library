package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.LivingHurtEventForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_HURT;

public class LivingHurtEventForge1_18_2 extends LivingHurtEventForge {
    
    @SubscribeEvent
    public static void onEvent(LivingHurtEvent event) {
        LIVING_HURT.invoke(event);
    }
}