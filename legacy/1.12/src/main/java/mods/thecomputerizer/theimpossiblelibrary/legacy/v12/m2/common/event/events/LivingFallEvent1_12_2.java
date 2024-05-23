package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingFallEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_FALL;

public class LivingFallEvent1_12_2 extends LivingFallEventWrapper<LivingFallEvent> {

    @SubscribeEvent
    public static void onEvent(LivingFallEvent event) {
        LIVING_FALL.invoke(event);
    }
    
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
