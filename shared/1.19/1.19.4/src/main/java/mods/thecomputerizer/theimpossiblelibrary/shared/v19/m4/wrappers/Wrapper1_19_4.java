package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.wrappers;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.common.biome.Biome1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.common.structure.Structure1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.registry.tab.CreativeTab1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.world.Dimension1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.world.World1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.wrappers.Wrapper1_19;
import org.jetbrains.annotations.Nullable;


public class Wrapper1_19_4 extends Wrapper1_19 {
    
    @Override public @Nullable <B> BiomeAPI<B> wrapBiome(@Nullable Object biome) {
        return getAs(biome,Biome1_19_4::new);
    }
    
    @Override public @Nullable <D> DimensionAPI<D> wrapDimension(WorldAPI<?> world, @Nullable Object dimension) {
        return getAs(dimension,type -> new Dimension1_19_4(world,type));
    }
    
    @Override public <S> StructureAPI<S> wrapStructure(@Nullable Object structure) {
        return getAs(structure,Structure1_19_4::new);
    }
    
    @Override public @Nullable <T> CreativeTabAPI<T> wrapTab(@Nullable Object tab) {
        return getAs(tab,CreativeTab1_19_4::new);
    }
    
    @Override public @Nullable <W> WorldAPI<W> wrapWorld(@Nullable Object world) {
        return getAs(world,World1_19_4::new);
    }
}