package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonLivingEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_KNOCKBACK;

public abstract class LivingKnockbackEventWrapper<E> extends CommonLivingEventType<E> {

    protected EventFieldWrapper<E,EntityAPI<?,?>> attacker;
    protected EventFieldWrapper<E,EntityAPI<?,?>> originalAttacker;
    protected EventFieldWrapper<E,Double> originalRatioX;
    protected EventFieldWrapper<E,Double> originalRatioZ;
    protected EventFieldWrapper<E,Float> originalStrength;
    protected EventFieldWrapper<E,Double> ratioX;
    protected EventFieldWrapper<E,Double> ratioZ;
    protected EventFieldWrapper<E,Float> strength;

    protected LivingKnockbackEventWrapper() {
        super(LIVING_KNOCKBACK);
    }

    public EntityAPI<?,?> getAttacker() {
        return this.attacker.get(this.event);
    }

    public EntityAPI<?,?> getOriginalAttacker() {
        return this.originalAttacker.get(this.event);
    }

    public double getOriginalRatioX() {
        return this.originalRatioX.get(this.event);
    }

    public double getOriginalRatioZ() {
        return this.originalRatioZ.get(this.event);
    }

    public float getOriginalStrength() {
        return this.originalStrength.get(this.event);
    }

    public double getRatioX() {
        return this.ratioX.get(this.event);
    }

    public double getRatioZ() {
        return this.ratioZ.get(this.event);
    }

    public float getStrength() {
        return this.strength.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.attacker = wrapAttackerField();
        this.originalAttacker = wrapOriginalAttackerField();
        this.originalRatioX = wrapOriginalRatioXField();
        this.originalRatioZ = wrapOriginalRatioZField();
        this.originalStrength = wrapOriginalStrengthField();
        this.ratioX = wrapRatioXField();
        this.ratioZ = wrapRatioZField();
        this.strength = wrapStrengthField();
    }

    public void setAttacker(EntityAPI<?,?> attacker) {
        this.attacker.set(this.event,attacker);
    }

    public void setRatioX(double ratioX) {
        this.ratioX.set(this.event,ratioX);
    }

    public void setRatioZ(double ratioZ) {
        this.ratioZ.set(this.event,ratioZ);
    }

    public void setStrength(float strength) {
        this.strength.set(this.event,strength);
    }

    protected abstract EventFieldWrapper<E,EntityAPI<?,?>> wrapAttackerField();
    protected abstract EventFieldWrapper<E,EntityAPI<?,?>> wrapOriginalAttackerField();
    protected abstract EventFieldWrapper<E,Double> wrapOriginalRatioXField();
    protected abstract EventFieldWrapper<E,Double> wrapOriginalRatioZField();
    protected abstract EventFieldWrapper<E,Float> wrapOriginalStrengthField();
    protected abstract EventFieldWrapper<E,Double> wrapRatioXField();
    protected abstract EventFieldWrapper<E,Double> wrapRatioZField();
    protected abstract EventFieldWrapper<E,Float> wrapStrengthField();
}