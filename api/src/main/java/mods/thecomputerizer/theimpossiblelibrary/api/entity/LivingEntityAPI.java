package mods.thecomputerizer.theimpossiblelibrary.api.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import org.joml.Vector3i;

public interface LivingEntityAPI<L> {

    default double getDistanceTo(EntityAPI<?> entity) {
        return getEntityAPI().getDistanceTo(entity);
    }

    default double getDistanceTo(BlockPosAPI<?> pos) {
        return getEntityAPI().getDistanceTo(pos);
    }

    default double getDistanceTo(Vector3i pos) {
        return getEntityAPI().getDistanceTo(pos);
    }

    EntityAPI<?> getEntityAPI();
    float getHealth();

    default float getHealthPercent() {
        return getMaxHealth()/getHealth();
    }

    L getLiving();
    float getMaxHealth();

    default BlockPosAPI<?> getPos() {
        return getEntityAPI().getPos();
    }

    default BlockPosAPI<?> getPosRounded() {
        return getEntityAPI().getPosRounded();
    }

    default ResourceLocationAPI<?> getRegistryName() {
        return getEntityAPI().getEntryAPI().getRegistryKey();
    }
}