package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.PotionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;

public class Potion1_12_2 extends PotionAPI<PotionType> {

    public Potion1_12_2(Object potion) {
        super((PotionType)potion);
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        this.wrapped.setRegistryName((ResourceLocation)registryName.unwrap());
    }
}