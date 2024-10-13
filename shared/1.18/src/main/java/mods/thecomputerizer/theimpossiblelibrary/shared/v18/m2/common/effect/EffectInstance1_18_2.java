package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectInstanceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.world.effect.MobEffectInstance;

public class EffectInstance1_18_2 extends EffectInstanceAPI<MobEffectInstance> {

    public EffectInstance1_18_2(Object instance) {
        super((MobEffectInstance)instance);
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