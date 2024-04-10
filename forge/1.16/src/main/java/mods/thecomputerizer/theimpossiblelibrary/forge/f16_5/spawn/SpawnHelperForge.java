package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.spawn;

import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelperAPI;
import net.minecraft.entity.LivingEntity;

public class SpawnHelperForge implements SpawnHelperAPI<LivingEntity> {

    @Override
    public SpawnEntryAPI<LivingEntity> getEntry(Class<? extends LivingEntity> clazz, int weight, int maxGroup, int minGroup) {
        return new SpawnEntryForge(clazz,weight,maxGroup,minGroup);
    }
}
