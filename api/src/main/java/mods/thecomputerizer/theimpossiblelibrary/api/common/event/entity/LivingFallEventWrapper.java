package mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity;

import lombok.Getter;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_FALL;

@Getter
public abstract class LivingFallEventWrapper<E> extends LivingEventWrapper<E> {

    protected float damageMultiplier;
    protected float distance;

    protected LivingFallEventWrapper() {
        super(LIVING_FALL);
    }

    public abstract void setDamageMultiplier(float damageMultiplier);
    public abstract void setDistance(float distance);
}