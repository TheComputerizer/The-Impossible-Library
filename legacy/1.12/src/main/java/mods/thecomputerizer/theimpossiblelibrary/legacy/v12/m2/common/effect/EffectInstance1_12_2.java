package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectInstanceAPI;
import net.minecraft.potion.PotionEffect;

public class EffectInstance1_12_2 extends EffectInstanceAPI<PotionEffect> {

    public EffectInstance1_12_2(PotionEffect effect) {
        super(effect);
    }

    @Override public int getAmplifier() {
        return this.wrapped.getAmplifier();
    }

    @Override public int getDuration() {
        return this.wrapped.getDuration();
    }

    @Override public EffectAPI<?> getEffect() {
        return WrapperHelper.wrapEffect(this.wrapped.getPotion());
    }
}