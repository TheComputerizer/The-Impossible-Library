package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;

public abstract class CommonLivingEventType<E> extends CommonEventWrapper<E> {

    protected EventFieldWrapper<E,LivingEntityAPI<?,?>> living;

    protected CommonLivingEventType(CommonType<?> type) {
        super(type);
    }

    @Override
    public void populate() {
        this.living = wrapLivingField();
    }

    protected abstract EventFieldWrapper<E,LivingEntityAPI<?,?>> wrapLivingField();
}
