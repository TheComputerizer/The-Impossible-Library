package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingDamageEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity.Damage1_12_2;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_DAMAGE;

public class LivingDamageEvent1_12_2 extends LivingDamageEventWrapper<LivingDamageEvent> {

    @SubscribeEvent
    public static void onEvent(LivingDamageEvent event) {
        LIVING_DAMAGE.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LivingDamageEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override public void setAmount(float amount) {
        this.event.setAmount(amount);
        this.damage.set(this.event,new Damage1_12_2(this.event.getSource(),amount));
    }

    @Override protected EventFieldWrapper<LivingDamageEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new Damage1_12_2(event.getSource(),this.event.getAmount()),null);
    }

    @Override protected EventFieldWrapper<LivingDamageEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingDamageEvent::getEntityLiving);
    }
}
