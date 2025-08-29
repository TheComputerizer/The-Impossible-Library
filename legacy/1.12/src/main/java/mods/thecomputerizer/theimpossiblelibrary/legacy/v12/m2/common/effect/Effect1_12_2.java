package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public class Effect1_12_2 extends EffectAPI<Potion> {

    public Effect1_12_2(Object effect) {
        super(effect);
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        if(Objects.nonNull(this.wrapped)) this.wrapped.setRegistryName((ResourceLocation)registryName.unwrap());
    }
}