package mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.structure;

import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.world.level.levelgen.structure.Structure;

public class Structure1_19 extends StructureAPI<Structure> {
    
    public Structure1_19(Object structure) {
        super((Structure)structure);
    }
    
    @Override public String getName() {
        return getRegistryName().getNamespace();
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        return RegistryHelper.getStructureRegistry().getKey(this.wrapped);
    }
}