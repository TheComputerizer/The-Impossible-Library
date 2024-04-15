package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import net.minecraft.potion.Potion;

public class Effect1_12_2 extends EffectAPI<Potion> {

    public Effect1_12_2(Potion effect) {
        super(effect);
    }

    @Override
    public ResourceLocationAPI<?> getRegistryName() {
        return new ResourceLocation1_12_2(this.getEffect().getRegistryName());
    }
}
