package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingAttackedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.CommonForgeEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public abstract class LivingAttackedEventForge extends LivingAttackedEventWrapper<LivingAttackEvent>
        implements CommonForgeEvent {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LivingAttackEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<LivingAttackEvent,DamageAPI<?>> wrapDamageField() {
        return wrapDamageGetter(LivingAttackEvent::getSource,1f);
    }

    @Override protected EventFieldWrapper<LivingAttackEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingAttackEvent::getEntity);
    }
}