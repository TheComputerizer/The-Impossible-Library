package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.registry.EntityEntry;

public class Living1_12_2 extends Entity1_12_2 implements LivingEntityAPI<EntityLivingBase> {

    private final EntityLivingBase living;

    public Living1_12_2(EntityLivingBase living) {
        this(living,getEntry(living));
    }

    public Living1_12_2(EntityLivingBase living, EntityEntry entry) {
        super(living,entry);
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
    public EntityLivingBase getLiving() {
        return this.living;
    }

    @Override
    public float getMaxHealth() {
        return this.living.getMaxHealth();
    }
}
