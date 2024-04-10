package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingDeathEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.entity.DamageForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class LivingDeathEventForge extends LivingDeathEventWrapper<LivingDeathEvent> {

    @Override
    protected EventFieldWrapper<LivingDeathEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new DamageForge(event.getSource(),1f),null);
    }

    @Override
    protected EventFieldWrapper<LivingDeathEvent,LivingEntityAPI<?>> wrapLivingField() {
        return wrapLivingGetter(LivingDeathEvent::getEntityLiving);
    }
}
