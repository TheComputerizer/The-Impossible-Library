package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectInstanceAPI;
import net.minecraft.potion.PotionEffect;

public class EffectInstance1_12_2 extends EffectInstanceAPI<PotionEffect> {

    public EffectInstance1_12_2(PotionEffect effect) {
        super(effect);
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
        return new Effect1_12_2(this.instance.getPotion());
    }
}
