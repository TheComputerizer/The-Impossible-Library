package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingFallEventWrapper;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingFallEvent;

import java.util.function.Function;

public class LivingFallEventForge extends LivingFallEventWrapper<LivingFallEvent> {

    @Override
    protected Function<LivingFallEvent,LivingEntity> getLivingFunc() {
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
