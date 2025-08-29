package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectInstanceAPI;
import net.minecraft.potion.PotionEffect;

public class EffectInstance1_12_2 extends EffectInstanceAPI<PotionEffect> {

    public EffectInstance1_12_2(Object effect) {
        super(effect);
    }

    @Override public int getAmplifier() {
        return getIfNotNullOrDefault(PotionEffect::getAmplifier,0);
    }

    @Override public int getDuration() {
        return getIfNotNullOrDefault(PotionEffect::getDuration,0);
    }

    @Override public EffectAPI<?> getEffect() {
        return getIfNotNull(w -> WrapperHelper.wrapEffect(w.getPotion()));
    }
}