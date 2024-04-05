package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.LivingEntityAPI;

import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_FALL;

@Getter
public abstract class LivingFallEventWrapper<E> extends CommonEventWrapper<E> {

    protected LivingEntityAPI<?> living;
    protected float damageMultiplier;
    protected float distance;

    protected LivingFallEventWrapper() {
        super(LIVING_FALL);
    }

    protected abstract Function<E,?> getLivingFunc();

    @Override
    public void populate() {
        this.living = WrapperHelper.wrapLivingEntity(getLivingFunc().apply(this.event));
    }

    public abstract void setDamageMultiplier(float damageMultiplier);
    public abstract void setDistance(float distance);
}