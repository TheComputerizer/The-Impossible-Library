package mods.thecomputerizer.theimpossiblelibrary.api.common.structure;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

public abstract class StructureAPI<S> extends AbstractWrapped<S> {

    protected StructureAPI(S structure) {
        super(structure);
    }

    public abstract String getName();
    public abstract ResourceLocationAPI<?> getRegistryName();
}