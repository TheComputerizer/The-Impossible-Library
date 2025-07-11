package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.world;

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

import java.util.Map.Entry;
import java.util.Objects;
import java.util.StringJoiner;

import static net.minecraft.core.registries.Registries.DIMENSION_TYPE;

public class Dimension1_19_4 extends DimensionAPI<DimensionType> {
    
    private final ResourceLocationAPI<?> cachedRegistryName;
    private final String name;
    
    public Dimension1_19_4(WorldAPI<?> world, Object dimension) {
        super(world,(DimensionType)dimension);
        LevelAccessor accessor = world.unwrap();
        this.cachedRegistryName = cacheRegistryName(accessor.registryAccess());
        this.name = calculateName();
    }
    
    private ResourceLocationAPI<?> cacheRegistryName(RegistryAccess access) {
        if(Objects.isNull(access) || Objects.isNull(this.wrapped)) return null;
        Registry<DimensionType> registry = access.registry(DIMENSION_TYPE).orElse(null);
        if(Objects.isNull(registry)) return null;
        ResourceKey<?> key = registry.getResourceKey(this.wrapped).orElse(null);
        if(Objects.isNull(key)) {
            for(Entry<ResourceKey<DimensionType>,DimensionType> entry : registry.entrySet()) {
                if(entry.getValue().equals(this.wrapped)) {
                    key = entry.getKey();
                    break;
                }
            }
        }
        return Objects.nonNull(key) ? WrapperHelper.wrapResourceLocation(key.location()) : null;
    }
    
    private String calculateName() {
        if(Objects.isNull(this.cachedRegistryName)) return null;
        String[] words = this.cachedRegistryName.getPath().split("_");
        StringJoiner joiner = new StringJoiner(" ");
        for(String word : words) joiner.add(TextHelper.capitalize(word));
        return joiner.toString();
    }
    
    @Override public String getName() {
        return this.name;
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        return this.cachedRegistryName;
    }
}