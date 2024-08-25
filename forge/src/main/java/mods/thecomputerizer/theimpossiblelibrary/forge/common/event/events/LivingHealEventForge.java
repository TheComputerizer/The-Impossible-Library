package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingHealEventWrapper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_HEAL;

public class LivingHealEventForge extends LivingHealEventWrapper<LivingHealEvent> {
    
    @SubscribeEvent
    public static void onEvent(LivingHealEvent event) {
        LIVING_HEAL.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LivingHealEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<LivingHealEvent,Float> wrapAmountField() {
        return wrapGenericBoth(LivingHealEvent::getAmount,LivingHealEvent::setAmount,0f);
    }

    @Override
    protected EventFieldWrapper<LivingHealEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingEvent::getEntityLiving);
    }
}