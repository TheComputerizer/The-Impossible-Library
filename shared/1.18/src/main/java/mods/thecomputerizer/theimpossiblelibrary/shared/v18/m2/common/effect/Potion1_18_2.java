package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.PotionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.alchemy.Potion;

public class Potion1_18_2 extends PotionAPI<Potion> {

    public Potion1_18_2(Object potion) {
        super(potion instanceof Holder<?> ? (Potion)((Holder<?>)potion).value() : (Potion)potion);
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        if(FORGE) this.wrapped.setRegistryName((ResourceLocation)registryName.unwrap());
    }
}