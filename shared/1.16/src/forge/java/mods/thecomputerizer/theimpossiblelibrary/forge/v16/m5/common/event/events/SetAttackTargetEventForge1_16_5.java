package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.SetAttackTargetEventForge;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_SET_TARGET;

public class SetAttackTargetEventForge1_16_5 extends SetAttackTargetEventForge<LivingSetAttackTargetEvent> {
    
    @SubscribeEvent
    public static void onEvent(LivingSetAttackTargetEvent event) {
        LIVING_SET_TARGET.invoke(event);
    }
    
    @Override protected EventFieldWrapper<LivingSetAttackTargetEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingSetAttackTargetEvent::getEntityLiving);
    }

    @Override protected EventFieldWrapper<LivingSetAttackTargetEvent,LivingEntityAPI<?,?>> wrapTargetField() {
        return wrapLivingGetter(LivingSetAttackTargetEvent::getTarget);
    }
}