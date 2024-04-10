package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.spawn;

import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.entity.LivingEntity;

public class SpawnEntryForge extends SpawnEntryAPI<LivingEntity> { //TODO

    public SpawnEntryForge(Class<? extends LivingEntity> clazz, int weight, int maxGroup, int minGroup) {
        super(clazz,weight,maxGroup,minGroup);
    }

    @Override
    public LivingEntity newInstance(WorldAPI<?> worldAPI) throws Exception {
        return null;
    }
}
