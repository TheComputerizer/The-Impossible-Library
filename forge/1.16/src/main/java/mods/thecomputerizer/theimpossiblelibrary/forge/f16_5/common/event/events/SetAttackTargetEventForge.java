package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.SetAttackTargetEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;

public class SetAttackTargetEventForge extends SetAttackTargetEventWrapper<LivingSetAttackTargetEvent> {

    @Override
    protected EventFieldWrapper<LivingSetAttackTargetEvent,LivingEntityAPI<?>> wrapLivingField() {
        return wrapLivingGetter(LivingSetAttackTargetEvent::getEntityLiving);
    }

    @Override
    protected EventFieldWrapper<LivingSetAttackTargetEvent,LivingEntityAPI<?>> wrapTargetField() {
        return wrapLivingGetter(LivingSetAttackTargetEvent::getTarget);
    }
}