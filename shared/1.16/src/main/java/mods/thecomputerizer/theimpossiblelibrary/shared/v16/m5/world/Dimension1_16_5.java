package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IWorld;

public class Dimension1_16_5 extends DimensionAPI<DimensionType> {
    
    private final DynamicRegistries registries;
    private final String name;
    
    public Dimension1_16_5(WorldAPI<?> world, Object dimension) {
        super(world,(DimensionType)dimension);
        this.registries = ((IWorld)world.getWrapped()).registryAccess();
        this.name = getRegistryName().getPath();
    }
    
    @Override public String getName() {
        return this.name;
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        return WrapperHelper.wrapResourceLocation(this.registries.dimensionTypes().getKey(this.wrapped));
    }
}