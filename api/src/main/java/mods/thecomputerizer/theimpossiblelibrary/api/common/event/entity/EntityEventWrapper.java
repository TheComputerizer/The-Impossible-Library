package mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.RegistryEntryEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;

@Getter
public abstract class EntityEventWrapper<E> extends RegistryEntryEventWrapper<E> {

    protected EntityAPI<?> entityAPI;

    protected EntityEventWrapper(CommonType<?> type) {
        super(type);
    }

    protected void setEntity(EntityAPI<?> api) {
        this.entityAPI = api;
        this.entryAPI = api.getEntryAPI();
    }
}