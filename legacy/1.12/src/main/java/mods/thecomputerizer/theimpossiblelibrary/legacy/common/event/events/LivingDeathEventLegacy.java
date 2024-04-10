package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingDeathEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.entity.DamageLegacy;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_DEATH;

public class LivingDeathEventLegacy extends LivingDeathEventWrapper<LivingDeathEvent> {

    @SubscribeEvent
    public static void onEvent(LivingDeathEvent event) {
        LIVING_DEATH.invoke(event);
    }

    @Override
    protected EventFieldWrapper<LivingDeathEvent,DamageAPI> wrapDamageField() {
        return wrapGenericGetter(event -> new DamageLegacy(event.getSource(),1f),null);
    }

    @Override
    protected EventFieldWrapper<LivingDeathEvent,LivingEntityAPI<?>> wrapLivingField() {
        return wrapLivingGetter(LivingDeathEvent::getEntityLiving);
    }
}
