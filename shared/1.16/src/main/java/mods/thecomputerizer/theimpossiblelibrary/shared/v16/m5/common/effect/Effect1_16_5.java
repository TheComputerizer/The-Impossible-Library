package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.world.effect.MobEffect;

import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

public class Effect1_16_5 extends EffectAPI<MobEffect> {

    public Effect1_16_5(Object effect) {
        super((MobEffect)effect);
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        if(FORGE) Methods.invoke(this.wrapped,"setRegistryName",(Object)registryName.unwrap());
    }
}