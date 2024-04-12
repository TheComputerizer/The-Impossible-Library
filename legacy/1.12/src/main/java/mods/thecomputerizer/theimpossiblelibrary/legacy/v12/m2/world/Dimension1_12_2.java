package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;

public class Dimension1_12_2 implements DimensionAPI<DimensionType> {

    private final DimensionType type;
    private final ResourceLocationAPI<ResourceLocation> res;

    public Dimension1_12_2(DimensionType type) {
        this.type = type;
        this.res = new ResourceLocation1_12_2(new ResourceLocation(type.getName()));
    }

    @Override
    public DimensionType getDimension() {
        return this.type;
    }

    @Override
    public ResourceLocationAPI<ResourceLocation> getRegistryName() {
        return this.res;
    }
}