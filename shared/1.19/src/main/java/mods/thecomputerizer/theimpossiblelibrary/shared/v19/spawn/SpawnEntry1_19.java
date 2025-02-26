package mods.thecomputerizer.theimpossiblelibrary.shared.v19.spawn;

import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.world.entity.LivingEntity;

public class SpawnEntry1_19 extends SpawnEntryAPI<LivingEntity> { //TODO

    public SpawnEntry1_19(Class<? extends LivingEntity> clazz, int weight, int maxGroup, int minGroup) {
        super(clazz,weight,maxGroup,minGroup);
    }

    @Override public LivingEntity newInstance(WorldAPI<?> worldAPI) {
        return null;
    }
}