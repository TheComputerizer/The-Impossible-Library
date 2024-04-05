package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.LivingEntityAPI;

import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ATTACKED;

@Getter
public abstract class LivingAttackedEventWrapper<E> extends CommonEventWrapper<E> {

    protected LivingEntityAPI<?> living;
    protected DamageAPI damage;

    protected LivingAttackedEventWrapper() {
        super(LIVING_ATTACKED);
    }

    protected abstract Function<E,?> getLivingFunc();

    @Override
    public void populate() {
        this.living = WrapperHelper.wrapLivingEntity(getLivingFunc().apply(this.event));
    }
}
