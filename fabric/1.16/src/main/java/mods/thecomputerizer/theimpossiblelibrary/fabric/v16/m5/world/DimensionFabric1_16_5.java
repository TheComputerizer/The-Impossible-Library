package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.world;

import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world.Dimension1_16_5;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;

public class DimensionFabric1_16_5 extends Dimension1_16_5<DimensionType> {

    private final RegistryAccess registries;
    private final String name;

    public DimensionFabric1_16_5(WorldAPI<?> world, Object dimension) {
        super(world,(DimensionType)dimension);
        this.registries = ((LevelAccessor)world.getWrapped()).registryAccess();
        this.name = getRegistryName().getPath();
    }

    @Override public String getName() {
        return this.name;
    }

    @Override public ResourceLocationAPI<?> getRegistryName() {
        return WrapperHelper.wrapResourceLocation(this.registries.dimensionTypes().getKey(this.wrapped));
    }
}