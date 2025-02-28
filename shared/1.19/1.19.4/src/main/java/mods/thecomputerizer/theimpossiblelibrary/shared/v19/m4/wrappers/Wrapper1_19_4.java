package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.wrappers;

import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.world.Dimension1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.world.World1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.wrappers.Wrapper1_19;

import javax.annotation.Nullable;

public class Wrapper1_19_4 extends Wrapper1_19 {
    
    @Override public @Nullable <D> DimensionAPI<D> wrapDimension(WorldAPI<?> world, @Nullable Object dimension) {
        return getAs(dimension,type -> new Dimension1_19_4(world,type));
    }
    
    @Override public @Nullable <W> WorldAPI<W> wrapWorld(@Nullable Object world) {
        return getAs(world,World1_19_4::new);
    }
}