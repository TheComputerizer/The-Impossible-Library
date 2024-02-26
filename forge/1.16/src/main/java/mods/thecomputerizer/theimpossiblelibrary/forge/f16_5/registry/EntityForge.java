package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import net.minecraft.entity.EntityType;

public class EntityForge extends RegistryEntryForge<EntityType<?>> implements EntityAPI<EntityType<?>> {

    private final EntityType<?> entity;

    protected EntityForge(EntityType<?> entry) {
        super(entry);
        this.entity = entry;
    }

    @Override
    public EntityType<?> getEntity() {
        return this.entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RegistryForge<EntityType<?>> getRegistry() {
        RegistryAPI<?> api = RegistryHelper.getEntityRegistry();
        return (RegistryForge<EntityType<?>>)api;
    }
}