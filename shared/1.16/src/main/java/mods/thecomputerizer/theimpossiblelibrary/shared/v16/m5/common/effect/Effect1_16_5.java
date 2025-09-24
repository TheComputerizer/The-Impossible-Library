package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.world.effect.MobEffect;

public class Effect1_16_5 extends EffectAPI<MobEffect> {

    public Effect1_16_5(Object effect) {
        super(effect);
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        setForgeRegistryName(this,registryName);
    }
}