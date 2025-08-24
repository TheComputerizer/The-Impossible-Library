package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events.LivingAttackedEventNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.entity.Damage1_21;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ATTACKED;

public class LivingAttackedEventNeoForge1_21 extends LivingAttackedEventNeoForge<EntityInvulnerabilityCheckEvent> {
    
    @SubscribeEvent
    public static void onEvent(EntityInvulnerabilityCheckEvent event) {
        LIVING_ATTACKED.invoke(event);
    }
    
    @Override protected EventFieldWrapper<EntityInvulnerabilityCheckEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new Damage1_21(event.getSource(),1f),null);
    }
}