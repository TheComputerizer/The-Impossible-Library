package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonLivingEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_HEAL;

public abstract class LivingHealEventWrapper<E> extends CommonLivingEventType<E> {

    protected EventFieldWrapper<E,Float> amount;

    protected LivingHealEventWrapper() {
        super(LIVING_HEAL);
    }

    public float getAmount() {
        return this.amount.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.amount = wrapAmountField();
    }

    public void setAmount(float amount) {
        this.amount.set(this.event,amount);
    }

    protected abstract EventFieldWrapper<E,Float> wrapAmountField();
}