package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world.Dimension1_16_5;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.DimensionType;

public class DimensionForge1_16_5 extends Dimension1_16_5<DimensionType> {

    private final DynamicRegistries registries;
    private final String name;

    public DimensionForge1_16_5(WorldForge1_16_5 world, DimensionType dimension) {
        super(world,dimension);
        this.registries = world.getWrapped().registryAccess();
        this.name = getRegistryName().getPath();
    }

    @Override public String getName() {
        return this.name;
    }

    @Override public ResourceLocationAPI<?> getRegistryName() {
        return WrapperHelper.wrapResourceLocation(this.registries.dimensionTypes().getKey(this.wrapped));
    }
}