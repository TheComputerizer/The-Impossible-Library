package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.PotionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraft.potion.Potion;

public class Potion1_16_5 extends PotionAPI<Potion> {

    public Potion1_16_5(Potion potion) {
        super(potion);
    }

    @Override
    public ResourceLocationAPI<?> getRegistryName() {
        return new ResourceLocation1_16_5(this.getPotion().getRegistryName());
    }
}
