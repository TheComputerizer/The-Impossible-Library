package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.spawn;

import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.Objects;

public class SpawnEntry1_12_2 extends SpawnEntryAPI<EntityLiving> {

    public SpawnEntry1_12_2(Class<? extends EntityLiving> clazz, int weight, int maxGroup, int minGroup) {
        super(clazz,weight,maxGroup,minGroup);
    }

    @Override public EntityLiving newInstance(WorldAPI<?> worldAPI) throws Exception {
        World world = worldAPI.unwrap();
        EntityEntry entry = EntityRegistry.getEntry(this.entityClass);
        return Objects.nonNull(entry) ? (EntityLiving)entry.newInstance(world) :
                this.entityClass.getConstructor(World.class).newInstance(world);
    }
}