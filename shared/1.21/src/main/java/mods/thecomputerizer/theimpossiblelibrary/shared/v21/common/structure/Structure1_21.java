package mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.structure;

import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.Objects;

import static net.minecraft.core.registries.Registries.STRUCTURE;

public class Structure1_21 extends StructureAPI<Structure> {
    
    public Structure1_21(Object structure) {
        super(structure instanceof Holder<?> ? ((Holder<?>)structure).value() : structure);
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        return getIfNotNull(w -> RegistryHelper.getStructureRegistry().getKey(w));
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName(WorldAPI<?> world) {
        LevelAccessor level = world.unwrap();
        return getRegistryName(level.registryAccess());
    }
    
    protected ResourceLocationAPI<?> getRegistryName(RegistryAccess access) {
        if(Objects.isNull(this.wrapped)) return null;
        Registry<Structure> registry = access.registry(STRUCTURE).orElse(null);
        return WrapperHelper.wrapResourceLocation(Objects.nonNull(registry) ? registry.getKey(this.wrapped) : null);
    }
}