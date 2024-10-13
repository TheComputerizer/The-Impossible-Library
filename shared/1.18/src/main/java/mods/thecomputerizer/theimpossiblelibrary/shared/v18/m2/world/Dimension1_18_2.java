package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.world;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.Objects;

import static net.minecraft.core.Registry.DIMENSION_TYPE_REGISTRY;

public class Dimension1_18_2 extends DimensionAPI<DimensionType> {
    
    private final RegistryAccess registries;
    private final String name;
    
    public Dimension1_18_2(WorldAPI<?> world, Object dimension) {
        super(world,(DimensionType)dimension);
        this.registries = ((LevelAccessor)world.getWrapped()).registryAccess();
        this.name = getRegistryName().getPath();
    }
    
    @Override public String getName() {
        return this.name;
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        Registry<DimensionType> registry = this.registries.registry(DIMENSION_TYPE_REGISTRY).orElse(null);
        ResourceLocation name = Objects.nonNull(registry) ? registry.getKey(this.wrapped) : null;
        return WrapperHelper.wrapResourceLocation(name);
    }
}