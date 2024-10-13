package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.LivingAttackedEventForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.entity.Damage1_18_2;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ATTACKED;

public class LivingAttackedEventForge1_18_2 extends LivingAttackedEventForge {
    
    @SubscribeEvent
    public static void onEvent(LivingAttackEvent event) {
        LIVING_ATTACKED.invoke(event);
    }
    
    @Override protected EventFieldWrapper<LivingAttackEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new Damage1_18_2(event.getSource(),1f),null);
    }
}