package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.structure;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.structure.Structure1_16_5;
import net.minecraft.world.level.levelgen.feature.StructureFeature;

import static net.minecraft.core.Registry.STRUCTURE_FEATURE;

public class StructureFabric1_16_5 extends Structure1_16_5<StructureFeature<?>> {
    
    public StructureFabric1_16_5(Object structure) {
        super((StructureFeature<?>)structure);
    }
    
    @Override public String getName() {
        return this.wrapped.getFeatureName();
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        return WrapperHelper.wrapResourceLocation(STRUCTURE_FEATURE.getKey(this.wrapped));
    }
}