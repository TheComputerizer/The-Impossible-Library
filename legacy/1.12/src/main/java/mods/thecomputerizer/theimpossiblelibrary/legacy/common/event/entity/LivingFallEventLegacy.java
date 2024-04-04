package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.LivingFallEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.LivingLegacy;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_FALL;

public class LivingFallEventLegacy extends LivingFallEventWrapper<LivingFallEvent> {

    @SubscribeEvent
    public static void onEvent(LivingFallEvent event) {
        LIVING_FALL.invoke(event);
    }

    private LivingFallEvent event;

    public LivingFallEventLegacy() {}

    public void setEvent(LivingFallEvent event) {
        this.event = event;
        setLiving(new LivingLegacy(event.getEntityLiving()));
        this.damageMultiplier = event.getDamageMultiplier();
        this.distance = event.getDistance();
    }

    @Override
    public void setDamageMultiplier(float damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
        this.event.setDamageMultiplier(damageMultiplier);
    }

    @Override
    public void setDistance(float distance) {
        this.distance = distance;
        this.event.setDistance(distance);
    }
}
