package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;

import java.util.Collection;

public abstract class CommonRegistryEventType<E,R extends RegistryEntryAPI<?>> extends CommonEventWrapper<E> {
    
    protected CommonRegistryEventType(CommonType<?> type) {
        super(type);
    }
    
    @Override public void populate() {}
    
    public abstract void register(R entry);
    
    @SafeVarargs public final void registerAll(R... entries) {
        for(R entry : entries) register(entry);
    }
    
    public void registerAll(Collection<R> entries) {
        entries.forEach(this::register);
    }
}
