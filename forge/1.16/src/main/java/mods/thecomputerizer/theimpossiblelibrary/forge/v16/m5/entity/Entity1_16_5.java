package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Box;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.RegistryEntry1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.Registry1_16_5;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.Objects;

public class Entity1_16_5 extends RegistryEntry1_16_5<EntityType<?>> implements EntityAPI<Entity> {

    private final Entity entity;

    public Entity1_16_5(Entity entity) {
        super(entity.getType());
        this.entity = entity;
    }

    @Override
    public Box getBoundingBox() {
        return Objects.nonNull(this.entity) ? getBoundingBox(this.entity.getBoundingBox()) : Box.ZERO;
    }

    public Box getBoundingBox(AxisAlignedBB aabb) {
        return new Box(aabb.minX,aabb.minY,aabb.minZ,aabb.maxX,aabb.maxY,aabb.maxZ);
    }

    @Override
    public Entity getEntity() {
        return this.entity;
    }

    @Override
    public RegistryEntryAPI<EntityType<?>> getEntryAPI() {
        return this;
    }

    @Override
    public boolean isLiving() {
        return this.entity instanceof LivingEntity;
    }

    @Override
    public boolean isPlayer() {
        return this.entity instanceof PlayerEntity;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Registry1_16_5<EntityType<?>> getRegistry() {
        return (Registry1_16_5<EntityType<?>>)(RegistryAPI<?>)RegistryHelper.getEntityRegistry();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends EntityType<?>> getValueClass() {
        return (Class<? extends EntityType<?>>)this.entity.getType().getClass();
    }
}