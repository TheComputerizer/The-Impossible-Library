package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;

import javax.annotation.Nullable;

public abstract class CommonLivingDamageEventType<E> extends CommonLivingEventType<E> {

    protected EventFieldWrapper<E,DamageAPI> damage;

    protected CommonLivingDamageEventType(CommonType<?> type) {
        super(type);
    }

    public @Nullable DamageAPI getDamageAPI() {
        return this.damage.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.damage = wrapDamageField();
    }

    protected abstract EventFieldWrapper<E,DamageAPI> wrapDamageField();
}