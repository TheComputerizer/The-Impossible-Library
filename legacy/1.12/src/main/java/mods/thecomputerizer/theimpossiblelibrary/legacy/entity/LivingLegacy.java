package mods.thecomputerizer.theimpossiblelibrary.legacy.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.registry.EntityEntry;

public class LivingLegacy extends EntityLegacy implements LivingEntityAPI<EntityLivingBase> {

    private final EntityLivingBase living;

    public LivingLegacy(EntityLivingBase living) {
        this(living,getEntry(living));
    }

    public LivingLegacy(EntityLivingBase living, EntityEntry entry) {
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
