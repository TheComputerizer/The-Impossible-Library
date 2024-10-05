package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.effect.EffectInstance1_16_5;
import net.minecraft.world.effect.MobEffectInstance;

public class EffectInstanceFabric1_16_5 extends EffectInstance1_16_5<MobEffectInstance> {

    public EffectInstanceFabric1_16_5(Object instance) {
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