package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonLivingDamageEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.DamageAPI;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_HURT;

public abstract class LivingHurtEventWrapper<E> extends CommonLivingDamageEventType<E> {

    protected LivingHurtEventWrapper() {
        super(LIVING_HURT);
    }

    public float getAmount() {
        DamageAPI api = getDamageAPI();
        return Objects.nonNull(api) ? api.getAmount() : 0f;
    }

    public abstract void setAmount(float amount);
}