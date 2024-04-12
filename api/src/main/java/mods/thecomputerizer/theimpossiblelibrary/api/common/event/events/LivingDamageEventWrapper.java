package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonLivingDamageEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_DAMAGE;

public abstract class LivingDamageEventWrapper<E> extends CommonLivingDamageEventType<E> {

    protected LivingDamageEventWrapper() {
        super(LIVING_DAMAGE);
    }

    public float getAmount() {
        DamageAPI api = getDamageAPI();
        return Objects.nonNull(api) ? api.getAmount() : 0f;
    }

    public abstract void setAmount(float amount);
}