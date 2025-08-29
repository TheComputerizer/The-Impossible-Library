package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events.LivingDamageEventNeoForge;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_DAMAGE;

public class LivingDamageEventNeoForge1_20 extends LivingDamageEventNeoForge<LivingDamageEvent> {
    
    @SubscribeEvent
    public static void onEvent(LivingDamageEvent event) {
        LIVING_DAMAGE.invoke(event);
    }
    
    @Override public void setAmount(float amount) {
        this.event.setAmount(amount);
        super.setAmount(amount);
    }
    
    @Override protected EventFieldWrapper<LivingDamageEvent,DamageAPI<?>> wrapDamageField() {
        return wrapDamageGetter(LivingDamageEvent::getSource,LivingDamageEvent::getAmount);
    }
}