package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.effect.EffectInstance1_16_5;
import net.minecraft.potion.EffectInstance;

public class EffectInstanceForge1_16_5 extends EffectInstance1_16_5<EffectInstance> {

    public EffectInstanceForge1_16_5(EffectInstance instance) {
        super(instance);
    }

    @Override public int getAmplifier() {
        return this.wrapped.getAmplifier();
    }

    @Override public int getDuration() {
        return this.wrapped.getDuration();
    }

    @Override public EffectAPI<?> getEffect() {
        return WrapperHelper.wrapEffect(this.wrapped.getEffect());
    }
}