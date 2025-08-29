package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectInstanceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.world.effect.MobEffectInstance;

public class EffectInstance1_16_5 extends EffectInstanceAPI<MobEffectInstance> {

    public EffectInstance1_16_5(Object instance) {
        super(instance);
    }
    
    @Override public int getAmplifier() {
        return getIfNotNullOrDefault(MobEffectInstance::getAmplifier,0);
    }
    
    @Override public int getDuration() {
        return getIfNotNullOrDefault(MobEffectInstance::getDuration,0);
    }
    
    @Override public EffectAPI<?> getEffect() {
        return getIfNotNull(w -> WrapperHelper.wrapEffect(w.getEffect()));
    }
}