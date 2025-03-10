package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.SetAttackTargetEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_SET_TARGET;
import static net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent.LivingTargetType.MOB_TARGET;

public class SetAttackTargetEventNeoForge extends SetAttackTargetEventWrapper<LivingChangeTargetEvent> {
    
    @SubscribeEvent
    public static void onEvent(LivingChangeTargetEvent event) {
        if(event.getTargetType()==MOB_TARGET) LIVING_SET_TARGET.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LivingChangeTargetEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<LivingChangeTargetEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingChangeTargetEvent::getEntity);
    }

    @Override protected EventFieldWrapper<LivingChangeTargetEvent,LivingEntityAPI<?,?>> wrapTargetField() {
        return wrapLivingGetter(LivingChangeTargetEvent::getNewTarget);
    }
}