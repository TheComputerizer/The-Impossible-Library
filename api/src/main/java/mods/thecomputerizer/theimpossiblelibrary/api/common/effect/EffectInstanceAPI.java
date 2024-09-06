package mods.thecomputerizer.theimpossiblelibrary.api.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.util.AbstractWrapped;

public abstract class EffectInstanceAPI<I> extends AbstractWrapped<I> {

    protected EffectInstanceAPI(I instance) {
        super(instance);
    }

    @IndirectCallers public abstract int getAmplifier();
    public abstract int getDuration();
    public abstract EffectAPI<?> getEffect();
}