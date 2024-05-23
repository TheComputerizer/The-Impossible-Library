package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.SetAttackTargetEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_SET_TARGET;

public class SetAttackTargetEvent1_12_2 extends SetAttackTargetEventWrapper<LivingSetAttackTargetEvent> {

    @SubscribeEvent
    public static void onEvent(LivingSetAttackTargetEvent event) {
        LIVING_SET_TARGET.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LivingSetAttackTargetEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override
    protected EventFieldWrapper<LivingSetAttackTargetEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingSetAttackTargetEvent::getEntityLiving);
    }

    @Override
    protected EventFieldWrapper<LivingSetAttackTargetEvent,LivingEntityAPI<?,?>> wrapTargetField() {
        return wrapLivingGetter(LivingSetAttackTargetEvent::getTarget);
    }
}