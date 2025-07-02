package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;

public abstract class CommonLivingEventType<E> extends CommonEntityEventType<E> {

    protected EventFieldWrapper<E,LivingEntityAPI<?,?>> living;

    protected CommonLivingEventType(CommonType<?> type) {
        super(type);
    }
    
    public EntityAPI<?,?> getEntity() {
        return getLivingEntity();
    }
    
    public LivingEntityAPI<?,?> getLivingEntity() {
        return this.living.get(this.event);
    }

    @Override public void populate() {
        this.living = wrapLivingField();
    }
    
    protected final EventFieldWrapper<E,EntityAPI<?,?>> wrapEntityField() {
        return null;
    }

    protected abstract EventFieldWrapper<E,LivingEntityAPI<?,?>> wrapLivingField();
}
