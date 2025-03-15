package mods.thecomputerizer.theimpossiblelibrary.shared.v21.spawn;

import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelperAPI;
import net.minecraft.world.entity.LivingEntity;

public class SpawnHelper1_21 implements SpawnHelperAPI<LivingEntity> {

    @Override public SpawnEntryAPI<LivingEntity> getEntry(Class<? extends LivingEntity> clazz, int weight, int maxGroup, int minGroup) {
        return new SpawnEntry1_21(clazz, weight, maxGroup, minGroup);
    }
}
