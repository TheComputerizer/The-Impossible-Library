package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.DamageAPI;

public abstract class CommonLivingDamageEventType<E> extends CommonLivingEventType<E> {

    protected EventFieldWrapper<E,DamageAPI> damage;

    protected CommonLivingDamageEventType(CommonType<?> type) {
        super(type);
    }

    @Override
    protected void populate() {
        super.populate();
        this.damage = wrapDamageField();
    }

    protected abstract EventFieldWrapper<E,DamageAPI> wrapDamageField();
}