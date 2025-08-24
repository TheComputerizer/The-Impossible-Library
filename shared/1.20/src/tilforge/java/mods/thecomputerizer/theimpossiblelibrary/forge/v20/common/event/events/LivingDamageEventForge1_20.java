package mods.thecomputerizer.theimpossiblelibrary.forge.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.LivingDamageEventForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.entity.Damage1_20;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_DAMAGE;

public class LivingDamageEventForge1_20 extends LivingDamageEventForge {
    
    @SubscribeEvent
    public static void onEvent(LivingDamageEvent event) {
        LIVING_DAMAGE.invoke(event);
    }
    
    @Override public void setAmount(float amount) {
        this.event.setAmount(amount);
        this.damage.set(this.event,new Damage1_20(this.event.getSource(),amount));
    }
    
    @Override protected EventFieldWrapper<LivingDamageEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new Damage1_20(event.getSource(),1f),null);
    }
}