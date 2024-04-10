package mods.thecomputerizer.theimpossiblelibrary.legacy.spawn;

import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.world.WorldLegacy;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.Objects;

public class SpawnEntryLegacy extends SpawnEntryAPI<EntityLiving> {

    public SpawnEntryLegacy(Class<? extends EntityLiving> clazz, int weight, int maxGroup, int minGroup) {
        super(clazz,weight,maxGroup,minGroup);
    }

    @Override
    public EntityLiving newInstance(WorldAPI<?> worldAPI) throws Exception {
        World world = ((WorldLegacy)worldAPI).getWorld();
        EntityEntry entry = EntityRegistry.getEntry(this.entityClass);
        return Objects.nonNull(entry) ? (EntityLiving)entry.newInstance(world) :
                this.entityClass.getConstructor(World.class).newInstance(world);
    }
}
