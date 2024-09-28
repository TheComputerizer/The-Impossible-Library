package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.spawn;

import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelperAPI;
import net.minecraft.entity.LivingEntity;

public class SpawnHelper1_16_5 implements SpawnHelperAPI<LivingEntity> {

    @Override public SpawnEntryAPI<LivingEntity> getEntry(Class<? extends LivingEntity> clazz, int weight, int maxGroup, int minGroup) {
        return new SpawnEntry1_16_5(clazz, weight, maxGroup, minGroup);
    }
}
