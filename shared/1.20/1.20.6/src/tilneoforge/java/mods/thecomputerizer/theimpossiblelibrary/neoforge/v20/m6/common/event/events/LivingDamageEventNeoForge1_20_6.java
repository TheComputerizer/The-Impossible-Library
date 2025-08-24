package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events.LivingDamageEventNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.entity.Damage1_20;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_DAMAGE;

public class LivingDamageEventNeoForge1_20_6 extends LivingDamageEventNeoForge<LivingDamageEvent> {
    
    @SubscribeEvent
    public static void onEvent(LivingDamageEvent event) {
        LIVING_DAMAGE.invoke(event);
    }
    
    @Override public void setAmount(float amount) {
        this.event.setAmount(amount);
        this.damage.set(this.event,new Damage1_20(this.event.getSource(), amount));
    }
    
    @Override protected EventFieldWrapper<LivingDamageEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new Damage1_20(event.getSource(),1f),null);
    }
}