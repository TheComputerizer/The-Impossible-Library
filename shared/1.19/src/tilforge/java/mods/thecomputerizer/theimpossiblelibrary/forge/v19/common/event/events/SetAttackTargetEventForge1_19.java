package mods.thecomputerizer.theimpossiblelibrary.forge.v19.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.SetAttackTargetEventForge;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_SET_TARGET;

public class SetAttackTargetEventForge1_19 extends SetAttackTargetEventForge<LivingChangeTargetEvent> {
    
    @SubscribeEvent
    public static void onEvent(LivingChangeTargetEvent event) {
        LIVING_SET_TARGET.invoke(event);
    }
    
    @Override protected EventFieldWrapper<LivingChangeTargetEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingChangeTargetEvent::getEntity);
    }

    @Override protected EventFieldWrapper<LivingChangeTargetEvent,LivingEntityAPI<?,?>> wrapTargetField() {
        return wrapLivingGetter(LivingChangeTargetEvent::getNewTarget);
    }
}