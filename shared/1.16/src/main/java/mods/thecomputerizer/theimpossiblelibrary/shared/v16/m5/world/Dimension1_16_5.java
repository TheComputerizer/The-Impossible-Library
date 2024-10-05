package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world;

import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

public abstract class Dimension1_16_5<D> extends DimensionAPI<D> {

    public Dimension1_16_5(WorldAPI<?> world, D dimension) {
        super(world,dimension);
    }
}