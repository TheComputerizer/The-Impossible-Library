package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingFallEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class LivingFallEvent1_16_5 extends LivingFallEventWrapper<LivingFallEvent> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LivingFallEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<LivingFallEvent,Float> wrapDamageMultiplierField() {
        return wrapGenericBoth(LivingFallEvent::getDamageMultiplier,LivingFallEvent::setDamageMultiplier,0f);
    }

    @Override
    protected EventFieldWrapper<LivingFallEvent,Float> wrapDistanceField() {
        return wrapGenericBoth(LivingFallEvent::getDistance,LivingFallEvent::setDistance,0f);
    }

    @Override
    protected EventFieldWrapper<LivingFallEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingFallEvent::getEntityLiving);
    }
}
