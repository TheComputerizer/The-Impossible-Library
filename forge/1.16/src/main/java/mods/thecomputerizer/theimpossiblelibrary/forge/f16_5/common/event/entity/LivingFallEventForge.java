package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.LivingFallEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.entity.LivingForge;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class LivingFallEventForge extends LivingFallEventWrapper<LivingEntity> {

    private final LivingFallEvent event;

    public LivingFallEventForge(LivingFallEvent event) {
        super(new LivingForge(event.getEntityLiving()),event.getDamageMultiplier(),event.getDistance());
        this.event = event;
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
