package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class Living1_16_5 extends Entity1_16_5 implements LivingEntityAPI<LivingEntity> {

    private final LivingEntity living;

    public Living1_16_5(LivingEntity living) {
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
