package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

public class Effect1_18_2 extends EffectAPI<MobEffect> {

    public Effect1_18_2(Object effect) {
        super(effect instanceof Holder<?> ? (MobEffect)((Holder<?>)effect).value() : (MobEffect)effect);
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        if(FORGE) this.wrapped.setRegistryName((ResourceLocation)registryName.unwrap());
    }
}