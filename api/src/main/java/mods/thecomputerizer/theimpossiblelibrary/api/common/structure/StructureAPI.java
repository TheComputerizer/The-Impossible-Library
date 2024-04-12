package mods.thecomputerizer.theimpossiblelibrary.api.common.structure;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

@Getter
public abstract class StructureAPI<S> {

    protected final S structure;

    protected StructureAPI(S structure) {
        this.structure = structure;
    }

    public abstract String getName();
    public abstract ResourceLocationAPI<?> getRegistryName();
}