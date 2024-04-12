package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.registry.EntityEntry;

import static mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.entity.Entity1_12_2.getEntry;

public class Living1_12_2 implements LivingEntityAPI<EntityLivingBase> {

    private final EntityAPI<Entity> entity;
    private final EntityLivingBase living;

    public Living1_12_2(EntityLivingBase living) {
        this(living,getEntry(living));
    }

    public Living1_12_2(EntityLivingBase living, EntityEntry entry) {
        this.entity = new Entity1_12_2(living,entry);
        this.living = living;
    }

    @Override
    public EntityAPI<Entity> getEntityAPI() {
        return this.entity;
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
