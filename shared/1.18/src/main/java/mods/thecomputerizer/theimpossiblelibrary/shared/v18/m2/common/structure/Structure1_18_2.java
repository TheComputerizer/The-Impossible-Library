package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.structure;

import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;

import java.util.Objects;

import static net.minecraft.core.Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY;

public class Structure1_18_2 extends StructureAPI<ConfiguredStructureFeature<?,?>> {
    
    public Structure1_18_2(Object structure) {
        super(structure instanceof Holder<?> ? (ConfiguredStructureFeature<?,?>)((Holder<?>)structure).value() :
                      (ConfiguredStructureFeature<?,?>)structure);
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        return RegistryHelper.getStructureRegistry().getKey(this.wrapped);
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName(WorldAPI<?> world) {
        LevelAccessor level = world.unwrap();
        return getRegistryName(level.registryAccess());
    }
    
    private ResourceLocationAPI<?> getRegistryName(RegistryAccess access) {
        Registry<ConfiguredStructureFeature<?,?>> registry = access.registry(CONFIGURED_STRUCTURE_FEATURE_REGISTRY).orElse(null);
        return WrapperHelper.wrapResourceLocation(Objects.nonNull(registry) ? registry.getKey(this.wrapped) : null);
    }
}