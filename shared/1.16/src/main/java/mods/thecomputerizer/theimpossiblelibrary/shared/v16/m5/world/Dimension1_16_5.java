package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Function;

public class Dimension1_16_5 extends DimensionAPI<DimensionType> {
    
    private final ResourceLocationAPI<?> cachedRegistryName;
    private final String name;
    
    public Dimension1_16_5(WorldAPI<?> world, Object dimension) {
        super(world,(DimensionType)dimension);
        this.cachedRegistryName = cacheRegistryName(world);
        this.name = calculateName();
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
    
    @Override protected Function<Object,Object> levelLocator() {
        return level -> {
            ResourceKey<Level> key = level instanceof Level ? ((Level)level).dimension() : null;
            return Objects.nonNull(key) ? key.location() : null;
        };
    }
}