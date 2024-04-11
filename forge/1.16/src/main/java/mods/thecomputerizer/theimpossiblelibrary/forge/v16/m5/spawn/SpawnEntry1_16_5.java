package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.spawn;

import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.entity.LivingEntity;

public class SpawnEntry1_16_5 extends SpawnEntryAPI<LivingEntity> { //TODO

    public SpawnEntry1_16_5(Class<? extends LivingEntity> clazz, int weight, int maxGroup, int minGroup) {
        super(clazz,weight,maxGroup,minGroup);
    }

    @Override
    public LivingEntity newInstance(WorldAPI<?> worldAPI) throws Exception {
        return null;
    }
}
