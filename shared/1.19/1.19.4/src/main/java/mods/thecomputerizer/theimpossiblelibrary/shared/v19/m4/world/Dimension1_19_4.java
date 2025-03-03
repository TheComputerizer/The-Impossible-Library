package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.world;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.Objects;
import java.util.StringJoiner;

import static net.minecraft.core.registries.Registries.DIMENSION_TYPE;

public class Dimension1_19_4 extends DimensionAPI<DimensionType> {
    
    private final RegistryAccess registries;
    private final String name;
    
    public Dimension1_19_4(WorldAPI<?> world, Object dimension) {
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
        Registry<DimensionType> registry = this.registries.registry(DIMENSION_TYPE).orElse(null);
        ResourceLocation name = Objects.nonNull(registry) ? registry.getKey(this.wrapped) : null;
        return WrapperHelper.wrapResourceLocation(name);
    }
}