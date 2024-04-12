package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.structure;

import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;

public class Structure1_12_2 extends StructureAPI<StructureRef> {

    public Structure1_12_2(StructureRef structure) {
        super(structure);
    }

    @Override
    public String getName() {
        return this.structure.getName();
    }

    @Override
    public ResourceLocationAPI<?> getRegistryName() {
        return new ResourceLocation1_12_2(this.structure.getId());
    }
}
