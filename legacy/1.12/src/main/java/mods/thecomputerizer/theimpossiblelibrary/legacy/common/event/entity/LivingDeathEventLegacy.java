package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.LivingDeathEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.DamageLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.LivingLegacy;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_DEATH;

public class LivingDeathEventLegacy extends LivingDeathEventWrapper<LivingDeathEvent> {

    @SubscribeEvent
    public static void onEvent(LivingDeathEvent event) {
        LIVING_DEATH.invoke(event);
    }

    public LivingDeathEventLegacy() {}

    public void setEvent(LivingDeathEvent event) {
        setLiving(new LivingLegacy(event.getEntityLiving()));
        this.damage = new DamageLegacy(event.getSource(),1f);
    }
}
