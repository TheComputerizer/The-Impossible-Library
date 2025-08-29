package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.LivingAttackedEventForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ATTACKED;

public class LivingAttackedEventForge1_18_2 extends LivingAttackedEventForge {
    
    @SubscribeEvent
    public static void onEvent(LivingAttackEvent event) {
        LIVING_ATTACKED.invoke(event);
    }
}