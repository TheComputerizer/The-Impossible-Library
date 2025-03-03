package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.common.structure;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.structure.Structure1_19;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.Objects;

import static net.minecraft.core.registries.Registries.STRUCTURE;

public class Structure1_19_4 extends Structure1_19 {
    
    public Structure1_19_4(Object structure) {
        super(structure);
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName(WorldAPI<?> world) {
        LevelAccessor level = world.unwrap();
        return getRegistryName(level.registryAccess());
    }
    
    @Override protected ResourceLocationAPI<?> getRegistryName(RegistryAccess access) {
        Registry<Structure> registry = access.registry(STRUCTURE).orElse(null);
        return WrapperHelper.wrapResourceLocation(Objects.nonNull(registry) ? registry.getKey(this.wrapped) : null);
    }
}