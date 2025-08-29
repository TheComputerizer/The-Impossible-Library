package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingHurtEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.CommonForgeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public abstract class LivingHurtEventForge extends LivingHurtEventWrapper<LivingHurtEvent>
        implements CommonForgeEvent {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setAmount(float amount) {
        this.event.setAmount(amount);
        super.setAmount(amount);
    }
    
    @Override public void setEvent(LivingHurtEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<LivingHurtEvent,DamageAPI<?>> wrapDamageField() {
        return wrapDamageGetter(LivingHurtEvent::getSource,LivingHurtEvent::getAmount);
    }
    
    @Override protected EventFieldWrapper<LivingHurtEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingHurtEvent::getEntity);
    }
}