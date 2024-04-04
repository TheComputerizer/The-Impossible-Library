package mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.LivingEntityAPI;

@Getter
public abstract class LivingEventWrapper<E> extends EntityEventWrapper<E> {

    protected LivingEntityAPI<?> livingAPI;

    protected LivingEventWrapper(CommonType<?> type) {
        super(type);
    }

    protected void setLiving(LivingEntityAPI<?> api) {
        this.livingAPI = api;
        setEntity(api.getEntityAPI());
    }
}