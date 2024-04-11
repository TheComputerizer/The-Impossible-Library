package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.spawn;

import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelperAPI;
import net.minecraft.entity.EntityLiving;

public class SpawnHelper1_12_2 implements SpawnHelperAPI<EntityLiving> {

    @Override
    public SpawnEntryAPI<EntityLiving> getEntry(Class<? extends EntityLiving> clazz, int weight, int maxGroup, int minGroup) {
        return new SpawnEntry1_12_2(clazz,weight,maxGroup,minGroup);
    }
}
