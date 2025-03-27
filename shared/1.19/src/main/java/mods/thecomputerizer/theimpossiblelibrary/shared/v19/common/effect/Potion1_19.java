package mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.PotionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.core.Holder;
import net.minecraft.world.item.alchemy.Potion;

public class Potion1_19 extends PotionAPI<Potion> {

    public Potion1_19(Object potion) {
        super(potion instanceof Holder<?> ? (Potion)((Holder<?>)potion).value() : (Potion)potion);
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName); //There is no built-in registryName field for forge in 1.19.+
    }
}