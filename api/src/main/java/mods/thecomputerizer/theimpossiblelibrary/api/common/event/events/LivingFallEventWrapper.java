package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonLivingEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_FALL;

public abstract class LivingFallEventWrapper<E> extends CommonLivingEventType<E> {

    protected EventFieldWrapper<E,Float> damageMultiplier;
    protected EventFieldWrapper<E,Float> distance;

    protected LivingFallEventWrapper() {
        super(LIVING_FALL);
    }

    public float getDamageMultiplier() {
        return this.damageMultiplier.get(this.event);
    }

    public float getDistance() {
        return this.distance.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.damageMultiplier = wrapDamageMultiplierField();
        this.distance = wrapDistanceField();
    }

    public void setDamageMultiplier(float multiplier) {
        this.damageMultiplier.set(this.event,multiplier);
    }

    public void setDistance(float distance) {
        this.distance.set(this.event,distance);
    }

    protected abstract EventFieldWrapper<E,Float> wrapDamageMultiplierField();
    protected abstract EventFieldWrapper<E,Float> wrapDistanceField();
}