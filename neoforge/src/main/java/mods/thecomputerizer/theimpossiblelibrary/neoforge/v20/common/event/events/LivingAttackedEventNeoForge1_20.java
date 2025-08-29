package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events.LivingAttackedEventNeoForge;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingAttackEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ATTACKED;

public class LivingAttackedEventNeoForge1_20 extends LivingAttackedEventNeoForge<LivingAttackEvent> {
    
    @SubscribeEvent
    public static void onEvent(LivingAttackEvent event) {
        LIVING_ATTACKED.invoke(event);
    }
    
    @Override protected EventFieldWrapper<LivingAttackEvent,DamageAPI<?>> wrapDamageField() {
        return wrapDamageGetter(LivingAttackEvent::getSource,LivingAttackEvent::getAmount);
    }
}