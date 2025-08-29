package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingDeathEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.CommonForgeEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public abstract class LivingDeathEventForge extends LivingDeathEventWrapper<LivingDeathEvent>
        implements CommonForgeEvent {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LivingDeathEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<LivingDeathEvent,DamageAPI<?>> wrapDamageField() {
        return wrapDamageGetter(LivingDeathEvent::getSource,1f);
    }

    @Override protected EventFieldWrapper<LivingDeathEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingDeathEvent::getEntity);
    }
}
