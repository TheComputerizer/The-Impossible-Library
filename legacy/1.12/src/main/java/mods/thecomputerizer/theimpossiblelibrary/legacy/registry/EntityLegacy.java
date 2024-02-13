package mods.thecomputerizer.theimpossiblelibrary.legacy.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import net.minecraftforge.fml.common.registry.EntityEntry;

public class EntityLegacy extends RegistryEntryLegacy<EntityEntry> implements EntityAPI<EntityEntry> {

    private final EntityEntry entity;

    protected EntityLegacy(EntityEntry entry) {
        super(entry);
        this.entity = entry;
    }

    @Override
    public EntityEntry getEntity() {
        return this.entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RegistryLegacy<EntityEntry> getRegistry() {
        RegistryAPI<?> api = RegistryHelper.getEntityRegistry();
        return (RegistryLegacy<EntityEntry>)api;
    }
}