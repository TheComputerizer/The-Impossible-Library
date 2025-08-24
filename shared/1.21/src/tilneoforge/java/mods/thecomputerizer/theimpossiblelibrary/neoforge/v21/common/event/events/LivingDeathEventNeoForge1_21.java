package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events.LivingDeathEventNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.entity.Damage1_21;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_DEATH;

public class LivingDeathEventNeoForge1_21 extends LivingDeathEventNeoForge {
    
    @SubscribeEvent
    public static void onEvent(LivingDeathEvent event) {
        LIVING_DEATH.invoke(event);
    }
    
    @Override protected EventFieldWrapper<LivingDeathEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new Damage1_21(event.getSource(),1f),null);
    }
}