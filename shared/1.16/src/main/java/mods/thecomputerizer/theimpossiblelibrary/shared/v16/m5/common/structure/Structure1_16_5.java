package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.structure;

import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.world.gen.feature.structure.Structure;

public class Structure1_16_5 extends StructureAPI<Structure<?>> {
    
    public Structure1_16_5(Object structure) {
        super((Structure<?>)structure);
    }
    
    @Override public String getName() {
        return this.wrapped.getFeatureName();
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        return RegistryHelper.getStructureRegistry().getKey(this.wrapped);
    }
}