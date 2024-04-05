package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingFallEventWrapper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_FALL;

public class LivingFallEventLegacy extends LivingFallEventWrapper<LivingFallEvent> {

    @SubscribeEvent
    public static void onEvent(LivingFallEvent event) {
        LIVING_FALL.invoke(event);
    }

    @Override
    protected Function<LivingFallEvent,EntityLivingBase> getLivingFunc() {
        return LivingFallEvent::getEntityLiving;
    }

    @Override
    public void populate() {
        super.populate();
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
