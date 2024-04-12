package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.Registry1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.RegistryEntry1_12_2;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Objects;

public class Entity1_12_2 extends RegistryEntry1_12_2<EntityEntry> implements EntityAPI<Entity> {

    public static @Nullable EntityEntry getEntry(Entity entity) {
        ResourceLocation key = EntityList.getKey(entity);
        return Objects.nonNull(key) ? ForgeRegistries.ENTITIES.getValue(key) : null;
    }

    private final Entity entity;
    private final EntityEntry entry;

    public Entity1_12_2(Entity entity) {
        this(entity,getEntry(entity));
    }

    public Entity1_12_2(Entity entity, EntityEntry entry) {
        super(entry);
        this.entity = entity;
        this.entry = entry;
    }

    @Override
    public Box getBoundingBox() {
        return Objects.nonNull(this.entity) ? getBoundingBox(this.entity.getEntityBoundingBox()) : Box.ZERO;
    }

    public Box getBoundingBox(AxisAlignedBB aabb) {
        return new Box(aabb.minX,aabb.minY,aabb.minZ,aabb.maxX,aabb.maxY,aabb.maxZ);
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
    public BlockPosAPI<?> getPos() {
        return PosHelper.getPos(this.entity.getPosition());
    }

    @Override
    public BlockPosAPI<?> getPosRounded() {
        return PosHelper.getPos(new BlockPos((int)(Math.round(this.entity.posX*2d)/2d),
                (int)(Math.round(this.entity.posY*2d)/2d),(int)(Math.round(this.entity.posZ*2d)/2d)));
    }

    @Override
    public boolean isAnimal() {
        return this.entity instanceof EntityAnimal;
    }

    @Override
    public boolean isLiving() {
        return this instanceof Living1_12_2 || this.entity instanceof EntityLivingBase;
    }

    @Override
    public boolean isPlayer() {
        return this instanceof Living1_12_2 || this.entity instanceof EntityPlayer;
    }

    @Override
    public boolean isOwnedBy(EntityAPI<?> owner) {
        return this.entity instanceof EntityTameable && ((EntityTameable)this.entity).getOwner()==owner.getEntity();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Registry1_12_2<EntityEntry> getRegistry() {
        return (Registry1_12_2<EntityEntry>)(RegistryAPI<?>)RegistryHelper.getEntityRegistry();
    }

    @Override
    public Class<? extends EntityEntry> getValueClass() {
        return this.entry.getClass();
    }
}