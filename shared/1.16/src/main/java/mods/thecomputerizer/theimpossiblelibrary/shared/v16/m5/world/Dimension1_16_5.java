package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.Objects;
import java.util.StringJoiner;

public class Dimension1_16_5 extends DimensionAPI<DimensionType> {
    
    private final RegistryAccess registries;
    private final String name;
    
    public Dimension1_16_5(WorldAPI<?> world, Object dimension) {
        super(world,(DimensionType)dimension);
        this.registries = ((LevelAccessor)world.getWrapped()).registryAccess();
        this.name = calculateName();
    }
    
    private String calculateName() {
        ResourceLocationAPI<?> registryName = getRegistryName();
        if(Objects.isNull(registryName)) return null;
        String[] words = registryName.getPath().split("_");
        StringJoiner joiner = new StringJoiner(" ");
        for(String word : words) joiner.add(TextHelper.capitalize(word));
        return joiner.toString();
    }
    
    @Override public String getName() {
        return this.name;
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        return WrapperHelper.wrapResourceLocation(this.registries.dimensionTypes().getKey(this.wrapped));
    }
}