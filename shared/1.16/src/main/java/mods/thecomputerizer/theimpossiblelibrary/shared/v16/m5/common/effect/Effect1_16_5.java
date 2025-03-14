package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;

public class Effect1_16_5 extends EffectAPI<Effect> {

    public Effect1_16_5(Object effect) {
        super((Effect)effect);
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        if(FORGE) this.wrapped.setRegistryName((ResourceLocation)registryName.unwrap());
    }
}