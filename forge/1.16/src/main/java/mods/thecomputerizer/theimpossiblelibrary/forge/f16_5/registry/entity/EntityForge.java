package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.RegistryEntryForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.RegistryForge;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

@SuppressWarnings("unchecked")
public class EntityForge extends RegistryEntryForge<EntityType<?>> implements EntityAPI<Entity> {

    private final Entity entity;

    public EntityForge(Entity entity) {
        super(entity.getType());
        this.entity = entity;
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
    protected RegistryForge<EntityType<?>> getRegistry() {
        return (RegistryForge<EntityType<?>>)(RegistryAPI<?>)RegistryHelper.getEntityRegistry();
    }

    @Override
    public Class<? extends EntityType<?>> getValueClass() {
        return (Class<? extends EntityType<?>>)this.entity.getType().getClass();
    }
}