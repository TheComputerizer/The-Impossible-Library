package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.SetAttackTargetEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;

public class SetAttackTargetEvent1_16_5 extends SetAttackTargetEventWrapper<LivingSetAttackTargetEvent> {
    
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