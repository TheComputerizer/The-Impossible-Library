package mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.RegistryEntryLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.RegistryLegacy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Objects;

public class EntityLegacy extends RegistryEntryLegacy<EntityEntry> implements EntityAPI<Entity> {

    public static @Nullable EntityEntry getEntry(Entity entity) {
        ResourceLocation key = EntityList.getKey(entity);
        return Objects.nonNull(key) ? ForgeRegistries.ENTITIES.getValue(key) : null;
    }

    private final Entity entity;
    private final EntityEntry entry;

    public EntityLegacy(Entity entity) {
        this(entity,getEntry(entity));
    }

    public EntityLegacy(Entity entity, EntityEntry entry) {
        super(entry);
        this.entity = entity;
        this.entry = entry;
    }

    @Override
    public Entity getEntity() {
        return this.entity;
    }

    @Override
    public RegistryEntryAPI<EntityEntry> getEntryAPI() {
        return this;
    }

    @Override
    public boolean isLiving() {
        return this instanceof LivingLegacy || this.entity instanceof EntityLivingBase;
    }

    @Override
    public boolean isPlayer() {
        return this instanceof LivingLegacy || this.entity instanceof EntityPlayer;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RegistryLegacy<EntityEntry> getRegistry() {
        return (RegistryLegacy<EntityEntry>)(RegistryAPI<?>)RegistryHelper.getEntityRegistry();
    }

    @Override
    public Class<? extends EntityEntry> getValueClass() {
        return this.entry.getClass();
    }
}