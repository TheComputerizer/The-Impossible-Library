package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events.LivingHurtEventNeoForge;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_HURT;

public class LivingHurtEventNeoForge1_20 extends LivingHurtEventNeoForge<LivingHurtEvent> {
    
    @SubscribeEvent
    public static void onEvent(LivingHurtEvent event) {
        LIVING_HURT.invoke(event);
    }
    
    @Override public void setAmount(float amount) {
        this.event.setAmount(amount);
        super.setAmount(amount);
    }
    
    @Override protected EventFieldWrapper<LivingHurtEvent,DamageAPI<?>> wrapDamageField() {
        return wrapDamageGetter(LivingHurtEvent::getSource,LivingHurtEvent::getAmount);
    }
}