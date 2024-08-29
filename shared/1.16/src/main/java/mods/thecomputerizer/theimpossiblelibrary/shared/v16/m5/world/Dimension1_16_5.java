package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world;

import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;

public class Dimension1_16_5 extends DimensionAPI<DimensionType> {

    private final DynamicRegistries registries;
    private final String name;

    public Dimension1_16_5(World1_16_5 world) {
        this(world,world.getWorld().dimensionType());
    }

    public Dimension1_16_5(World1_16_5 world, DimensionType dimension) {
        super(world,dimension);
        this.registries = world.getWorld().registryAccess();
        this.name = getRegistryName().getPath();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ResourceLocation1_16_5 getRegistryName() {
        Registry<DimensionType> registry = this.registries.dimensionTypes();
        return new ResourceLocation1_16_5(registry.getKey(this.dimension));
    }
}
