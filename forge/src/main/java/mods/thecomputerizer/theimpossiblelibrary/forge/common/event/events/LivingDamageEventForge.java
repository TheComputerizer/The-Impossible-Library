package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingDamageEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.CommonForgeEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public abstract class LivingDamageEventForge extends LivingDamageEventWrapper<LivingDamageEvent>
        implements CommonForgeEvent {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LivingDamageEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<LivingDamageEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingDamageEvent::getEntity);
    }
}
