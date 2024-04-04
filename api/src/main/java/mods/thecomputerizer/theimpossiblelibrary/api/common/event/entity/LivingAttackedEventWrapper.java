package mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.DamageAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ATTACKED;

@Getter
public abstract class LivingAttackedEventWrapper<E> extends LivingEventWrapper<E> {

    protected DamageAPI damage;

    protected LivingAttackedEventWrapper() {
        super(LIVING_ATTACKED);
    }
}
