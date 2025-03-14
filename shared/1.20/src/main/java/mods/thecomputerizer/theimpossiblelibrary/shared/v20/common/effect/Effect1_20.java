package mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.world.effect.MobEffect;

public class Effect1_20 extends EffectAPI<MobEffect> {

    public Effect1_20(Object effect) {
        super((MobEffect)effect);
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName); //There is no built-in registryName field for forge in 1.19.+
    }
}