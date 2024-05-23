package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingHurtEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.entity.Damage1_16_5;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class LivingHurtEvent1_16_5 extends LivingHurtEventWrapper<LivingHurtEvent> {
    
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
        this.damage.set(this.event,new Damage1_16_5(this.event.getSource(),amount));
    }

    @Override
    protected EventFieldWrapper<LivingHurtEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new Damage1_16_5(event.getSource(),this.event.getAmount()),null);
    }

    @Override
    protected EventFieldWrapper<LivingHurtEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingHurtEvent::getEntityLiving);
    }
}