package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.structure;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.structure.Structure1_16_5;
import net.minecraft.world.gen.feature.structure.Structure;

public class StructureForge1_16_5 extends Structure1_16_5<Structure<?>> {
    
    public StructureForge1_16_5(Object structure) {
        super((Structure<?>)structure);
    }
    
    @Override public String getName() {
        return this.wrapped.getFeatureName();
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        return WrapperHelper.wrapResourceLocation(this.wrapped.getRegistryName());
    }
}