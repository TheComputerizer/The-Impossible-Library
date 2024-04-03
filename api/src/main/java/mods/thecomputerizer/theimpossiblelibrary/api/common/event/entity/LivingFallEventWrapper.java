package mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CancellableEvent;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.LivingEntityAPI;

public abstract class LivingFallEventWrapper<L> extends CancellableEvent implements LivingEventAPI {

    private final LivingEntityAPI<L> living;
    @Getter protected float damageMultiplier;
    @Getter protected float distance;

    protected LivingFallEventWrapper(LivingEntityAPI<L> living, float damageMultiplier, float distance) {
        this.living = living;
        this.damageMultiplier = damageMultiplier;
        this.distance = distance;
    }

    @Override
    public RegistryEntryAPI<?> getEntry() {
        return this.living.getEntityAPI().getEntryAPI();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> V getEntryValue() {
        return (V)this.living.getEntityAPI().getEntryAPI();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> E getEntity() {
        return (E)this.living;
    }

    @Override
    public EntityAPI<L> getEntityAPI() {
        return this.living.getEntityAPI();
    }

    @Override
    public LivingEntityAPI<L> getLivingAPI() {
        return this.living;
    }

    public abstract void setDamageMultiplier(float damageMultiplier);
    public abstract void setDistance(float distance);
}