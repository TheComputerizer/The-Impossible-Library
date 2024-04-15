package mods.thecomputerizer.theimpossiblelibrary.api.common.effect;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;

@Getter
public abstract class PotionAPI<P> implements RegistryEntryAPI<P> {

    protected final P potion;

    protected PotionAPI(P potion) {
        this.potion = potion;
    }

    @Override
    public P getValue() {
        return this.potion;
    }

    @SuppressWarnings("unchecked")
    public Class<? extends P> getValueClass() {
        return (Class<? extends P>)this.potion.getClass();
    }
}