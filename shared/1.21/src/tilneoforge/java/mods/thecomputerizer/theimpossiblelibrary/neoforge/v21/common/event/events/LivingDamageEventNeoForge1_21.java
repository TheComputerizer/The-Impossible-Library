package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events.LivingDamageEventNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.entity.Damage1_21;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_DAMAGE;

public class LivingDamageEventNeoForge1_21 extends LivingDamageEventNeoForge<LivingIncomingDamageEvent> {
    
    @SubscribeEvent
    public static void onEvent(LivingDamageEvent event) {
        LIVING_DAMAGE.invoke(event);
    }
    
    @Override public void setAmount(float amount) {
        this.event.setAmount(amount);
        this.damage.set(this.event,new Damage1_21(this.event.getSource(), amount));
    }
    
    @Override protected EventFieldWrapper<LivingIncomingDamageEvent,DamageAPI<?>> wrapDamageField() {
        return wrapGenericGetter(event -> new Damage1_21(event.getSource(),1f),null);
    }
}