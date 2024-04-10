package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonLivingEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_SET_TARGET;

public abstract class SetAttackTargetEventWrapper<E> extends CommonLivingEventType<E> {

    protected EventFieldWrapper<E,LivingEntityAPI<?>> target;

    protected SetAttackTargetEventWrapper() {
        super(LIVING_SET_TARGET);
    }

    public LivingEntityAPI<?> getTarget() {
        return this.target.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.target = wrapTargetField();
    }

    protected abstract EventFieldWrapper<E,LivingEntityAPI<?>> wrapTargetField();
}