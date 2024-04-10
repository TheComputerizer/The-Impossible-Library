package mods.thecomputerizer.theimpossiblelibrary.legacy.spawn;

import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelperAPI;
import net.minecraft.entity.EntityLiving;

public class SpawnHelperLegacy implements SpawnHelperAPI<EntityLiving> {

    @Override
    public SpawnEntryAPI<EntityLiving> getEntry(Class<? extends EntityLiving> clazz, int weight, int maxGroup, int minGroup) {
        return new SpawnEntryLegacy(clazz,weight,maxGroup,minGroup);
    }
}
