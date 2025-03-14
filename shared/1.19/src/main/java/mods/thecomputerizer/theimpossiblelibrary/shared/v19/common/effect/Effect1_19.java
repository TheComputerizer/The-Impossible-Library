package mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.world.effect.MobEffect;

public class Effect1_19 extends EffectAPI<MobEffect> {

    public Effect1_19(Object effect) {
        super((MobEffect)effect);
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName); //There is no built-in registryName field for forge in 1.19.+
    }
}