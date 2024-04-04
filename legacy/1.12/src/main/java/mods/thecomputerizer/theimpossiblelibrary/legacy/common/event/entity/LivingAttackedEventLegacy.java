package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.LivingAttackedEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.DamageLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.LivingLegacy;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ATTACKED;

public class LivingAttackedEventLegacy extends LivingAttackedEventWrapper<LivingAttackEvent> {

    @SubscribeEvent
    public static void onEvent(LivingAttackEvent event) {
        LIVING_ATTACKED.invoke(event);
    }

    public LivingAttackedEventLegacy() {}

    public void setEvent(LivingAttackEvent event) {
        setLiving(new LivingLegacy(event.getEntityLiving()));
        this.damage = new DamageLegacy(event.getSource(),event.getAmount());
    }
}
