package mods.thecomputerizer.theimpossiblelibrary.shared.v20.world;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.Objects;
import java.util.StringJoiner;

import static net.minecraft.core.registries.Registries.DIMENSION_TYPE;

public class Dimension1_20 extends DimensionAPI<DimensionType> {
    
    private final RegistryAccess registries;
    private final String name;
    
    public Dimension1_20(WorldAPI<?> world, Object dimension) {
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
        if(Objects.isNull(this.registries) || Objects.isNull(this.wrapped)) return null;
        Registry<DimensionType> registry = this.registries.registry(DIMENSION_TYPE).orElse(null);
        if(Objects.isNull(registry)) return null;
        ResourceKey<DimensionType> key = registry.getResourceKey(this.wrapped).orElse(null);
        return Objects.nonNull(key) ? WrapperHelper.wrapResourceLocation(key.location()) : null;
    }
}