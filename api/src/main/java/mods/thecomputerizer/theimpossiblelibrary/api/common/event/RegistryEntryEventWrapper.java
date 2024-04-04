package mods.thecomputerizer.theimpossiblelibrary.api.common.event;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;

@Getter
public abstract class RegistryEntryEventWrapper<E> extends CommonEventWrapper<E> {

    protected RegistryEntryAPI<?> entryAPI;

    protected RegistryEntryEventWrapper(CommonType<?> type) {
        super(type);
    }
}