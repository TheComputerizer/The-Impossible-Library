package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.LivingEntityAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class LivingForge extends EntityForge implements LivingEntityAPI<LivingEntity> {

    private final LivingEntity living;

    public LivingForge(LivingEntity living) {
        super(living);
        this.living = living;
    }

    @Override
    public EntityAPI<Entity> getEntityAPI() {
        return this;
    }

    @Override
    public float getHealth() {
        return this.living.getHealth();
    }

    @Override
    public LivingEntity getLiving() {
        return this.living;
    }

    @Override
    public float getMaxHealth() {
        return this.living.getMaxHealth();
    }
}
