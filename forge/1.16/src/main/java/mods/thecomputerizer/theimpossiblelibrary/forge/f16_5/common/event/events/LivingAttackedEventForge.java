package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingAttackedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.entity.DamageForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class LivingAttackedEventForge extends LivingAttackedEventWrapper<LivingAttackEvent> {

    @Override
    protected EventFieldWrapper<LivingAttackEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new DamageForge(event.getSource(),1f),null);
    }

    @Override
    protected EventFieldWrapper<LivingAttackEvent,LivingEntityAPI<?>> wrapLivingField() {
        return wrapLivingGetter(LivingAttackEvent::getEntityLiving);
    }
}