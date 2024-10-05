package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI.Pseudo;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.world.DimensionType;

public class Dimension1_12_2 extends DimensionAPI<DimensionType> {

    private final ResourceLocationAPI<Integer> res;

    public Dimension1_12_2(WorldAPI<?> world, Object type) {
        super(world,(DimensionType)type);
        this.res = new Pseudo<>(((DimensionType)type).getId());
    }

    @Override public String getName() {
        return this.wrapped.getName();
    }

    @Override public ResourceLocationAPI<Integer> getRegistryName() {
        return this.res;
    }
}