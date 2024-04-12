package mods.thecomputerizer.theimpossiblelibrary.api.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import org.joml.Vector3i;

public interface EntityAPI<E> {

    Box getBoundingBox();

    default double getDistanceTo(EntityAPI<?> entity) {
        return getPos().distanceTo(entity.getPos());
    }

    default double getDistanceTo(BlockPosAPI<?> pos) {
        return getPos().distanceTo(pos);
    }

    default double getDistanceTo(Vector3i pos) {
        return getPos().distanceTo(pos);
    }

    E getEntity();
    RegistryEntryAPI<?> getEntryAPI();
    BlockPosAPI<?> getPos();
    BlockPosAPI<?> getPosRounded();

    default ResourceLocationAPI<?> getRegistryName() {
        return getEntryAPI().getRegistryKey();
    }

    boolean isAnimal();
    boolean isLiving();
    boolean isPlayer();
    boolean isOwnedBy(EntityAPI<?> owner);
}
