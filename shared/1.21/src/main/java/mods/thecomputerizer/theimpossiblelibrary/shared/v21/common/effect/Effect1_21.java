package mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public class Effect1_21 extends EffectAPI<MobEffect> {

    public Effect1_21(Object effect) {
        super(effect instanceof Holder<?> ? ((Holder<?>)effect).value() : effect);
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName); //There is no built-in registryName field for forge in 1.19.+
    }
}