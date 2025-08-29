package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.structure;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

public class Structure1_12_2 extends StructureAPI<StructureRef> {

    public Structure1_12_2(Object structure) {
        super(structure);
    }

    @Override public String getName() {
        return getIfNotNull(StructureRef::getName);
    }

    @Override public ResourceLocationAPI<?> getRegistryName() {
        return getIfNotNull(w -> WrapperHelper.wrapResourceLocation(w.getId()));
    }
}