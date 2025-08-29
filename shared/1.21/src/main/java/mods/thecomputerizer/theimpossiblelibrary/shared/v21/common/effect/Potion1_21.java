package mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.PotionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.core.Holder;
import net.minecraft.world.item.alchemy.Potion;

public class Potion1_21 extends PotionAPI<Potion> {

    public Potion1_21(Object potion) {
        super(potion instanceof Holder<?> ? ((Holder<?>)potion).value() : potion);
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName); //There is no built-in registryName field for forge in 1.19.+
    }
}