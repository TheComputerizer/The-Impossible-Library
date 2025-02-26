package mods.thecomputerizer.theimpossiblelibrary.forge.v19.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.LivingDeathEventForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.entity.Damage1_19;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_DEATH;

public class LivingDeathEventForge1_19 extends LivingDeathEventForge {
    
    @SubscribeEvent
    public static void onEvent(LivingDeathEvent event) {
        LIVING_DEATH.invoke(event);
    }
    
    @Override protected EventFieldWrapper<LivingDeathEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new Damage1_19(event.getSource(),1f),null);
    }
}