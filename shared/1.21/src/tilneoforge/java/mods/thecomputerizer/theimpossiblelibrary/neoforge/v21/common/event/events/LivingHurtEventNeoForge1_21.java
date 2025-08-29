package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events.LivingHurtEventNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.entity.Damage1_21;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_HURT;

public class LivingHurtEventNeoForge1_21 extends LivingHurtEventNeoForge<Pre> {
    
    @SubscribeEvent
    public static void onEvent(Pre event) {
        LIVING_HURT.invoke(event);
    }
    
    @Override public void setAmount(float amount) {
        this.event.setNewDamage(amount);
        this.damage.set(this.event,new Damage1_21(this.event.getSource(),amount));
    }
    
    @Override protected EventFieldWrapper<Pre,DamageAPI<?>> wrapDamageField() {
        return wrapGenericGetter(event -> new Damage1_21(event.getSource(),1f),null);
    }
}