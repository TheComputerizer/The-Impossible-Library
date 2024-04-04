package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.LivingDamageEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.DamageLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.LivingLegacy;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_DAMAGE;

public class LivingDamageEventLegacy extends LivingDamageEventWrapper<LivingDamageEvent> {

    @SubscribeEvent
    public static void onEvent(LivingDamageEvent event) {
        LIVING_DAMAGE.invoke(event);
    }

    private LivingDamageEvent event;

    public LivingDamageEventLegacy() {}

    public void setEvent(LivingDamageEvent event) {
        this.event = event;
        setLiving(new LivingLegacy(event.getEntityLiving()));
        this.damage = new DamageLegacy(event.getSource(),event.getAmount());
    }

    @Override
    public void setAmount(float amount) {
        this.event.setAmount(amount);
        this.damage.setAmount(amount);
    }
}
