package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI.Pseudo;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import net.minecraft.world.DimensionType;

public class Dimension1_12_2 extends DimensionAPI<DimensionType> {

    private final ResourceLocationAPI<Integer> res;

    public Dimension1_12_2(World1_12_2 world) {
        this(world,world.getWorld().provider.getDimensionType());
    }

    public Dimension1_12_2(World1_12_2 world, DimensionType type) {
        super(world,type);
        this.res = new Pseudo<>(type.getId());
    }

    @Override
    public String getName() {
        return this.dimension.getName();
    }

    @Override
    public ResourceLocationAPI<Integer> getRegistryName() {
        return this.res;
    }
}