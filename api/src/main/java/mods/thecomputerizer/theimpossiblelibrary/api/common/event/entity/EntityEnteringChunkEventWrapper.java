package mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;

public abstract class EntityEnteringChunkEventWrapper<E> implements EntityEventAPI {

    private final EntityAPI<E> entity;
    @Getter protected int newX;
    @Getter protected int newZ;
    @Getter protected int oldX;
    @Getter protected int oldZ;

    protected EntityEnteringChunkEventWrapper(EntityAPI<E> entity, int newX, int newZ, int oldX, int oldZ) {
        this.entity = entity;
        this.newX = newX;
        this.newZ = newZ;
        this.oldX = oldX;
        this.oldZ = oldZ;
    }

    @Override
    public RegistryEntryAPI<?> getEntry() {
        return this.entity.getEntryAPI();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> V getEntryValue() {
        return (V)this.entity.getEntryAPI().getValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <EN> EN getEntity() {
        return (EN)this.entity.getEntity();
    }

    @Override
    public EntityAPI<E> getEntityAPI() {
        return this.entity;
    }

    public abstract void setNewX(int x);
    public abstract void setNewZ(int z);
    public abstract void setOldX(int x);
    public abstract void setOldZ(int z);
}
