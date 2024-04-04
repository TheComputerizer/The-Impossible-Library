package mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.DamageAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_DAMAGE;

@Getter
public abstract class LivingDamageEventWrapper<E> extends LivingEventWrapper<E> {

    protected DamageAPI damage;

    protected LivingDamageEventWrapper() {
        super(LIVING_DAMAGE);
    }

    public abstract void setAmount(float amount);
}
