package mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CancellableEvent;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.LivingEntityAPI;

public abstract class LivingDamageEventWrapper<L> extends CancellableEvent implements LivingEventAPI {

    private final LivingEntityAPI<L> living;
    @Getter private final DamageAPI damage;

    protected LivingDamageEventWrapper(LivingEntityAPI<L> living, DamageAPI damage) {
        this.living = living;
        this.damage = damage;
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

    public void setAmount(float amount) {
        this.damage.setAmount(amount);
        setAmountInner(amount);
    }

    protected abstract void setAmountInner(float amount);
}
