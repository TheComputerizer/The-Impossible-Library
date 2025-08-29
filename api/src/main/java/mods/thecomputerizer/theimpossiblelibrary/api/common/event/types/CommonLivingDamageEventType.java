package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;

public abstract class CommonLivingDamageEventType<E> extends CommonLivingEventType<E> implements CommonDamageEventType<E> {

    protected EventFieldWrapper<E,DamageAPI<?>> damage;

    protected CommonLivingDamageEventType(CommonType<?> type) {
        super(type);
    }
    
    public float getAmount() {
        return getDamageAmount();
    }

    @Override public EventFieldWrapper<E,DamageAPI<?>> getDamageField() {
        return this.damage;
    }
    
    @Override public E getSource() {
        return this.event;
    }

    @Override public void populate() {
        super.populate();
        this.damage = wrapDamageField();
    }
    
    public void setAmount(float amount) {
        setDamageAmount(amount);
    }
    

    protected abstract EventFieldWrapper<E,DamageAPI<?>> wrapDamageField();
}