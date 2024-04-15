package mods.thecomputerizer.theimpossiblelibrary.api.common.effect;

import lombok.Getter;

@Getter
public abstract class EffectInstanceAPI<I> {

    protected final I instance;

    protected EffectInstanceAPI(I instance) {
        this.instance = instance;
    }

    public abstract int getAmplifier();
    public abstract int getDuration();
    public abstract EffectAPI<?> getEffect();
}