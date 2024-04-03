package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.LivingFallEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.LivingLegacy;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class LivingFallEventLegacy extends LivingFallEventWrapper<EntityLivingBase> {

    private final LivingFallEvent event;

    public LivingFallEventLegacy(LivingFallEvent event) {
        super(new LivingLegacy(event.getEntityLiving()),event.getDamageMultiplier(),event.getDistance());
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
