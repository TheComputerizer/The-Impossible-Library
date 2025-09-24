package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.PotionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.world.item.alchemy.Potion;

public class Potion1_16_5 extends PotionAPI<Potion> {

    public Potion1_16_5(Object potion) {
        super(potion);
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        setForgeRegistryName(this,registryName);
    }
}