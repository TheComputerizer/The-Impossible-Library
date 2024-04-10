package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingFallEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class LivingFallEventForge extends LivingFallEventWrapper<LivingFallEvent> {

    @Override
    protected EventFieldWrapper<LivingFallEvent,Float> wrapDamageMultiplierField() {
        return wrapGenericBoth(LivingFallEvent::getDamageMultiplier,LivingFallEvent::setDamageMultiplier,0f);
    }

    @Override
    protected EventFieldWrapper<LivingFallEvent,Float> wrapDistanceField() {
        return wrapGenericBoth(LivingFallEvent::getDistance,LivingFallEvent::setDistance,0f);
    }

    @Override
    protected EventFieldWrapper<LivingFallEvent,LivingEntityAPI<?>> wrapLivingField() {
        return wrapLivingGetter(LivingFallEvent::getEntityLiving);
    }
}
