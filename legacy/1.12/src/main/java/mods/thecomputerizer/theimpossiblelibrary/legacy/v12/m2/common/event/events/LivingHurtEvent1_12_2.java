package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingHurtEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity.Damage1_12_2;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_HURT;

public class LivingHurtEvent1_12_2 extends LivingHurtEventWrapper<LivingHurtEvent> {

    @SubscribeEvent
    public static void onEvent(LivingHurtEvent event) {
        LIVING_HURT.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LivingHurtEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override
    public void setAmount(float amount) {
        this.event.setAmount(amount);
        this.damage.set(this.event,new Damage1_12_2(this.event.getSource(),amount));
    }

    @Override
    protected EventFieldWrapper<LivingHurtEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new Damage1_12_2(event.getSource(),this.event.getAmount()),null);
    }

    @Override
    protected EventFieldWrapper<LivingHurtEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingHurtEvent::getEntityLiving);
    }
}