package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingHealEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;

public class LivingHealEventForge extends LivingHealEventWrapper<LivingHealEvent> {

    @Override
    protected EventFieldWrapper<LivingHealEvent,Float> wrapAmountField() {
        return wrapGenericBoth(LivingHealEvent::getAmount,LivingHealEvent::setAmount,0f);
    }

    @Override
    protected EventFieldWrapper<LivingHealEvent,LivingEntityAPI<?>> wrapLivingField() {
        return wrapLivingGetter(LivingEvent::getEntityLiving);
    }
}