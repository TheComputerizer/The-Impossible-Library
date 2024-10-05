package mods.thecomputerizer.theimpossiblelibrary.api.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

public abstract class PotionAPI<P> extends AbstractWrapped<P> implements RegistryEntryAPI<P> {

    protected PotionAPI(P potion) {
        super(potion);
    }
}