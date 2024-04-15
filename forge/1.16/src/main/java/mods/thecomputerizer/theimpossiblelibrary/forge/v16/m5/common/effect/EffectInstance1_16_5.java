package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectInstanceAPI;
import net.minecraft.potion.EffectInstance;

public class EffectInstance1_16_5 extends EffectInstanceAPI<EffectInstance> {

    public EffectInstance1_16_5(EffectInstance instance) {
        super(instance);
    }

    @Override
    public int getAmplifier() {
        return this.instance.getAmplifier();
    }

    @Override
    public int getDuration() {
        return this.instance.getDuration();
    }

    @Override
    public EffectAPI<?> getEffect() {
        return new Effect1_16_5(this.instance.getEffect());
    }
}