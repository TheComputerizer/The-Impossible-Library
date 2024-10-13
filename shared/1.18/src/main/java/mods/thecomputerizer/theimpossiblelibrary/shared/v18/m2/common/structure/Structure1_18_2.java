package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.structure;

import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.world.level.levelgen.feature.StructureFeature;

public class Structure1_18_2 extends StructureAPI<StructureFeature<?>> {
    
    public Structure1_18_2(Object structure) {
        super((StructureFeature<?>)structure);
    }
    
    @Override public String getName() {
        return getRegistryName().getNamespace();
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        return RegistryHelper.getStructureRegistry().getKey(this.wrapped);
    }
}