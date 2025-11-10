package mods.thecomputerizer.theimpossiblelibrary.api.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.util.Collection;
import java.util.function.BiFunction;

public abstract class LivingEntityAPI<L,V> extends EntityAPI<L,V> {

    protected LivingEntityAPI(Object entity, Object type) {
        super(entity,type);
    }
    
    protected LivingEntityAPI(Object entity, Object type, BiFunction<L,String,Collection<?>> effectsGetter) {
        super(entity,type,effectsGetter);
    }

    public abstract float getHealth();
    
    @IndirectCallers
    public float getHealthPercent() {
        return getHealth()/getMaxHealth();
    }

    public abstract float getMaxHealth();
}