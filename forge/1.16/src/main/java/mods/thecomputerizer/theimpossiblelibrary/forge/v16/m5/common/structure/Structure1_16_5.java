package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.structure;

import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraft.world.gen.feature.structure.Structure;

public class Structure1_16_5 extends StructureAPI<Structure<?>> {

    public Structure1_16_5(Structure<?> structure) {
        super(structure);
    }

    @Override
    public String getName() {
        return this.structure.getFeatureName();
    }

    @Override
    public ResourceLocationAPI<?> getRegistryName() {
        return new ResourceLocation1_16_5(this.structure.getRegistryName());
    }
}