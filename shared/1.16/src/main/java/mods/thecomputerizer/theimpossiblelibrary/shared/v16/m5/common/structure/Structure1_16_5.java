package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.structure;

import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.world.level.levelgen.feature.StructureFeature;

public class Structure1_16_5 extends StructureAPI<StructureFeature<?>> {
    
    public Structure1_16_5(Object structure) {
        super(structure);
    }
    
    @Override public String getName() {
        return getIfNotNull(StructureFeature::getFeatureName);
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        return getIfNotNull(w -> RegistryHelper.getStructureRegistry().getKey(w));
    }
}